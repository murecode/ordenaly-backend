package com.app.ordenaly.services;

import com.app.ordenaly.models.User;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

  @Value("${security.jwt.expiration-minutes}")
  private long EXPIRATION_MINUTES;
  @Value("${security.jwt.secret-key}")
  private String SECRET_KEY;

  public String generateToken(User user, Map<String,Object> extraClaims) {

    //1.
    Date issueAt = new Date(System.currentTimeMillis());
    Date expiration = new Date( issueAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));

    //2.
    return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(issueAt)
            .setExpiration(expiration)
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .signWith(generateKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  //3.
  private Key generateKey() {
    byte[] secretKeyAsBytes = Decoders.BASE64.decode(SECRET_KEY);
    System.out.println(new String( secretKeyAsBytes ));
    return Keys.hmacShaKeyFor( secretKeyAsBytes );
  }

}

//1. Configuracion de tiempos de expedicion y caducidad del token en milisegundos
//2. Definicion de los claims que contendra la Carga util en el JSON
//3. Metodo para decodificar la contraseña encriptada en Base64
