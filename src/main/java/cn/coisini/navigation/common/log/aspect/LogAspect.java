package cn.coisini.navigation.common.log.aspect;

import cn.coisini.navigation.common.log.annotation.Log;
import cn.coisini.navigation.common.log.service.AsyncOperLogService;
import cn.coisini.navigation.model.pojos.AsyncOperLog;
import cn.coisini.navigation.utils.IdWorker;
import cn.coisini.navigation.utils.IpUtils;
import cn.coisini.navigation.utils.JwtUtil;
import com.alibaba.fastjson2.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * Author: xiaoxiang
 * Description: 操作日志记录处理
 */
@Aspect // 使用aop操作
@Component // 交给spring管理
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    //微服务切换为feign调用接口
    private final AsyncOperLogService asyncOperLogService;

    public LogAspect(AsyncOperLogService asyncOperLogService) {
        this.asyncOperLogService = asyncOperLogService;
    }

    /**
     * 处理完请求后执行
     *  (后置通知)
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {
        try {
            // 通过RequestContextHolder上下文请求对象
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            // 得到 ServletRequestAttributes对象
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            // 获取到所有请求内容
            assert sra != null;
            HttpServletRequest request = sra.getRequest();

            // *========数据库日志=========*//
            // new操作日志,向里面添加数据
            AsyncOperLog operLog = new AsyncOperLog();
            operLog.setId(String.valueOf(new IdWorker().nextId()));
            // 处理设置注解上的参数（标题、动作、操作人类别、返回参数）
            getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
            // 从token中取到 用户名、用户ID
            String token = request.getHeader("token");
            String userName = JwtUtil.getUsername(token);
            String userId = JwtUtil.getUserId(token);
            operLog.setOperUserId(userId);
            operLog.setOperName(userName);
            // 设置方法名称
            // 通过joinPoint参数得到类的名字
            String className = joinPoint.getTarget().getClass().getName();
            /// 得到方法的名字
            String methodName = joinPoint.getSignature().getName();
            // 设置Method值 例如：cn.coisini.navigation.controller.UserController.status()
            operLog.setMethod(className + "." + methodName + "()");
            // 设置RequestMethod请求方式 例如：GET
            operLog.setRequestMethod(request.getMethod());
            // 当前请求路径
            operLog.setOperUrl(request.getRequestURI());
            // 设置请求的地址
            String ip = IpUtils.getIp(request);
            // IP
            operLog.setOperIp(ip);
            // IP归属地
            operLog.setOperIpSource(IpUtils.getIp2region(ip));
            // IP 省市区
            operLog.setOperIpCity(IpUtils.getCityInfo(ip));
            // 设置当前日志状态(0正常 1异常)
            operLog.setStatus(false);
            // 状态(0正常 1异常)
            if (e != null) {
                operLog.setStatus(true);
                operLog.setErrorMsg(e.getMessage());
            }
            // 操作日志数据保存数据库
            asyncOperLogService.saveSysLog(operLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, AsyncOperLog operLog, Object jsonResult) throws Exception {
        // 设置标题 例如：用户管理
        operLog.setTitle(log.title());
        // 设置action动作 例如：STATUS
        operLog.setBusinessType(log.businessType().name());
        // 设置操作人类别 例如：MANAGE
        operLog.setOperatorType(log.operatorType().name());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。 异常的参数
            setRequestValue(joinPoint, operLog);
        }
        // 是否需要保存response，参数和值 例如：{"code":200,"data":"SUCCESS","message":"操作成功"}
        if (log.isSaveResponseData() && !ObjectUtils.isEmpty(jsonResult)) {
            operLog.setJsonResult(JSON.toJSONString(jsonResult));
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     */
    private void setRequestValue(JoinPoint joinPoint, AsyncOperLog operLog) {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            // 设置异常日志参数
            operLog.setOperParam(params);
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null) {
            for (Object o : paramsArray) {
                if (!ObjectUtils.isEmpty(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params.append(jsonObj.toString()).append(" ");
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}