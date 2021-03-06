-- TABLE
CREATE TABLE IF NOT EXISTS "OAUTH"."TB_TIPO_GRANT"
(
    "CD_TIPO_GRANT" BIGINT NOT NULL,
    "NOME" CHARACTER VARYING(100) NOT NULL,
    "DESCRICAO" CHARACTER VARYING(100),
    "DT_CADASTRO" TIMESTAMP WITH TIME ZONE NOT NULL,
    "DT_ATUALIZACAO" TIMESTAMP WITH TIME ZONE,
    "CD_USUARIO_CRIACAO" BIGINT NOT NULL,
    "CD_USUARIO_ATUALIZACAO" BIGINT,
    "FL_ATIVO" BOOLEAN NOT NULL
);

-- COMMENT
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT"."CD_TIPO_GRANT" IS 'CÓDIGO DO TIPO GRANT';
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT"."NOME" IS 'NOME DO TIPO GRANT';
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT"."DESCRICAO" IS 'DESCRIÇÃO DO TIPO GRANT';
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT"."DT_CADASTRO" IS 'DATA DE CADASTRO DO TIPO GRANT';
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT"."DT_ATUALIZACAO" IS 'DATA DE ATUALIZAÇÃO DO TIPO GRANT';
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT"."CD_USUARIO_CRIACAO" IS 'USUÁRIO DE CRIAÇÃO DO TIPO GRANT';
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT"."CD_USUARIO_ATUALIZACAO" IS 'USUÁRIO DE ATUALIZAÇÃO DO TIPO GRANT';
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT"."FL_ATIVO" IS '[TRUE/FALSE] - SINALIZAÇÃO DE ATIVO DO TIPO GRANT';

-- PRIMARY KEY 
ALTER TABLE "OAUTH"."TB_TIPO_GRANT" ADD CONSTRAINT "PK_TB_TIPO_GRANT" PRIMARY KEY ("CD_TIPO_GRANT");

-- UNIQUE KEY 
ALTER TABLE "OAUTH"."TB_TIPO_GRANT" ADD CONSTRAINT "UK_NOME_TB_TIPO_GRANT" UNIQUE ("NOME");

-- FOREIGN KEY
ALTER TABLE "OAUTH"."TB_TIPO_GRANT" ADD CONSTRAINT "FK_TGA_TB_USUARIO" FOREIGN KEY ("CD_USUARIO_CRIACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO");
ALTER TABLE "OAUTH"."TB_TIPO_GRANT" ADD CONSTRAINT "FK_TGB_TB_USUARIO" FOREIGN KEY ("CD_USUARIO_ATUALIZACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO"); 

