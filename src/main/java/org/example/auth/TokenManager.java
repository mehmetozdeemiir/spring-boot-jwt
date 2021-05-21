package org.example.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenManager {

    private static final int validity =5*60*1000;
    Key key=Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //aldığı username e göre token generate edicek,verilen username e token yaratıcak
    public String generateToken(String username){

       return Jwts.builder()
               .setSubject(username)
               .setIssuer("www.haydikodlayalim.com")//kim yarattı
               .setIssuedAt(new Date(System.currentTimeMillis()))//hangi tarihte yarattı-> oluşturulan an imzalandı,yaratıldı
               .setExpiration(new Date(System.currentTimeMillis()+validity))//ne zamana kadar geçerli
               .signWith(key)//imzalarken neler kullanabiliyoruz
               .compact();
       //token oluşturuldu token geri döndürülüyor
    }
    //token ın validate olup olmadığını doğru mu yanlıs mı dıye kontrol edicek
     public boolean tokenValidate(String token){
        if(getUsernameToken(token)!= null && isExpired(token)){
            return true;
        }
        return false;
    }

    //string token alıcak geriye string username dönücek.
   public String getUsernameToken(String token){
       Claims claims= getClaims(token);
       return claims.getSubject(); //username i token ın içerisinden çıkarmış oldum
   }
    public boolean isExpired(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();//parseClaimJws dicez içerisine token ı vericez bize bütün claimleri vericek. claim den kastımız:setIssuer claimdir,setExpiration bir claimdir. parser() metodunada secretkey ile bu işi çöz dememiz lazım
    }


}
