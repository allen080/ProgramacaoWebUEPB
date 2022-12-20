package com.progweb.DiarioEscolar.domain.enums;

public enum Authority {
    PROF(0,"ROLE_PROF"),
    ALUNO(1, "ROLE_ALUNO");
 
     private Integer codigo;
     private String descricao;
     
     private Authority(Integer codigo, String descricao) {
         this.codigo = codigo;
         this.descricao = descricao;
     }
 
     public Integer getCodigo() {
         return codigo;
     }
 
     public String getDescricao() {
         return descricao;
     }
 
     public static Authority toEnum(Integer cod){
         if(cod == null){
             return null;
         }
 
         for(Authority x : Authority.values()){
             if(cod.equals(x.getCodigo())){
                 return x;
             }
         }
 
         throw new IllegalArgumentException("Authority invalido");
     }
 
     
 }
