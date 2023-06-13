package com.example.webproject.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;


@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {

    private long expire;

    private String secret;

    private String header;

    //jdk8新增的时钟类
    private Clock clock = DefaultClock.INSTANCE;

    // 生成jwt
    public String getToken(String username) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)// 7天過期
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    public Claims getTokenClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    /**
     * 根据token获取失效时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getTokenClaim(token);
        return claimsResolver.apply(claims);
    }

    // 解析jwt
    public Claims getClaimByToken(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // jwt是否过期
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

}
