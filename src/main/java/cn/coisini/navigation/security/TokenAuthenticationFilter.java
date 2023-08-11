package cn.coisini.navigation.security;

import cn.coisini.navigation.utils.JwtUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: xiaoxiang
 * Description:
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final StringRedisTemplate stringRedisTemplate;

    public TokenAuthenticationFilter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        // 请求头token不为空
        if (!CharSequenceUtil.isBlank(token)) {
            String username = JwtUtil.getUsername(token);
            String userId = JwtUtil.getUserId(token);
            // 检验token且上下文对象没有Authentication
            if (!CharSequenceUtil.isBlank(username) && userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                logger.info("用户：" + userId + " " + username);
                String auth = stringRedisTemplate.opsForValue().get(username);
                List<Map> mapList = JSON.parseArray(auth, Map.class);
                assert mapList != null;
                List<SimpleGrantedAuthority> authority = mapList.stream().map(item -> new SimpleGrantedAuthority((String) item.get("authority"))).collect(Collectors.toList());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authority);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
