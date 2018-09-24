package slipp.stalk.service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import slipp.stalk.domain.Member;

public class JwtHelper {
    private final String privateKey;
    private final JwtDateHelper dateHelper;

    public JwtHelper(JwtProperties properties) {
        this.privateKey = properties.getPrivateKey();
        this.dateHelper = new JwtDateHelper(properties.getExpirationHour());
    }

    public JwtToken createToken(Member member) {
        String token = Jwts.builder()
                           .setExpiration(dateHelper.expirationDate())
                           .setIssuedAt(dateHelper.issuedDate())
                           .setSubject(String.valueOf(member.getEmail()))
                           .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(privateKey))
                           .compact();

        return new JwtToken(token);
    }
}