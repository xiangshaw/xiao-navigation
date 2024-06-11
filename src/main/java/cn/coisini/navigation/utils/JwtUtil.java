package cn.coisini.navigation.utils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * JWT加密工具类
 */
public class JwtUtil {
    // TOKEN的有效期一天（S）
    private static final Long TOKEN_TIME_OUT = 3600000L;// 60 * 60 *1000  一个小时
    // 加密KEY
    private static final String TOKEN_ENCRY_KEY = "CoisiniXiAo9ZNIoJiUXiaNg1PLdi6NI";
    // 最小刷新间隔(S)
    private static final int REFRESH_TIME = 300;

    // 生产ID(创建token)
    public static String getToken(String userId, String username){
        Map<String, Object> claimMaps = new HashMap<>();
        claimMaps.put("userId",userId);
        claimMaps.put("username",username);
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                //设置id
                .setId(UUID.randomUUID().toString())
                //签发时间
                .setIssuedAt(new Date(currentTime))
                //说明
                .setSubject("system")
                //签发者信息
                .setIssuer("coisini")
                //接收用户
                .setAudience("all")
                //数据压缩方式
                .compressWith(CompressionCodecs.GZIP)
                //加密方式
                .signWith(SignatureAlgorithm.HS512, generalKey())
                //过期时间戳
                .setExpiration(new Date(currentTime + TOKEN_TIME_OUT * 1000))
                //cla信息(手动设置的内容)
                .addClaims(claimMaps)
                .compact();
    }

    /**
     * 获取token中的claims信息
     * @param token
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(token);
    }

    /**
     * 获取payload body信息
     *
     * @param token
     * @return
     */
    public static Claims getClaimsBody(String token) {
        try {
            return getJws(token).getBody();
        }catch (ExpiredJwtException e){
            return null;
        }
    }

    /**
     * 获取hearder body信息
     *
     * @param token
     * @return
     */
    public static JwsHeader getHeaderBody(String token) {
        return getJws(token).getHeader();
    }

    /**
     * 获取用户名称
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        try {
            if (StringUtils.isEmpty(token)) return "";
            return (String) getJws(token).getBody().get("username");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取用户ID
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        try {
            if (StringUtils.isEmpty(token)) return null;
            String userId = (String) getJws(token).getBody().get("userId");
            return userId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 是否过期
     *
     * @param claims
     * @return -1：有效，0：有效，1：过期，2：过期
     */
    public static int verifyToken(Claims claims) {
        if(claims==null){
            return 1;
        }
        try {
            claims.getExpiration()
                    .before(new Date());
            // 需要自动刷新TOKEN
            if((claims.getExpiration().getTime()-System.currentTimeMillis())>REFRESH_TIME*1000){
                return -1;
            }else {
                return 0;
            }
        } catch (ExpiredJwtException ex) {
            return 1;
        }catch (Exception e){
            return 2;
        }
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(TOKEN_ENCRY_KEY.getBytes());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }


    //测试
    public static void main(String[] args) {
        // 生成带id的jwt字符串
        String token = JwtUtil.getToken("916", "coisini");
       // String token = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAE2LSw7CMAwF7-J1I5GfCb0Bx3ASL4LagnAigRB3x3wW7N680Tzg1BvM4FKJOaE1h-qLCZzZUEIy-6Bn9R5LjjBBow6zxeRidLgLE8jIWstdOq9vL6JYzk3a1pRpVGVaFt18u3xatL92CF-P6u13brTyX_x8AWae3UebAAAA.kPDbvysBpsUJOBAcJVSEl6A9y1PxUEbVGoJLy45nFE3jpr0kyiX1KPtyZ7kVoALv3YIShCg6d5IzjA6Ih_19Ew";
        System.out.println(token);
        String username = getUsername(token);
        System.out.println(username);
        String userId = getUserId(token);
        System.out.println(userId);

        // 解析生成的jwt字符串
        //Jws<Claims> jws = JwtUtil.getJws("eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAEWLQQqDQAwA_5KzC43daPQ30Y2QoraQFVpK_9548jbDMF94VIMRlqID9sKJCDHlCe-JZ16S9EqZUAtSBw2YVBix4xtnyi024McUt3-86nZ299D5aW67hctRwmVdg_X9ul6O16Lh7w86UoGUggAAAA.0xlqighSYD6erFl6Nfpn6NcaDMIdkOaeL1LslOxjIEZf4uvLmYgKDHDyLka7_url5lvNBta_vcQix-r3Z1zOmQ");
        // 从jws中，获取自定义的clqims
        // Claims claims = jws.getBody();
        // {jti=7a0711fb-6356-4bc2-9e6b-4f596134c4bd, iat=1680839661, sub=system, iss=coisini, aud=all, exp=1684439661, id=1}
        //System.out.println(claims);
        // 1
        //System.out.println(claims.get("id"));

        // 判断token是否过期
        //System.out.println(JwtUtil.verifyToken(jws.getBody()));

    }

}