-- TABLE
CREATE TABLE IF NOT EXISTS "OAUTH"."TB_TIPO_GRANT_APLICACAO"
(
    "CD_TIPO_GRANT" BIGINT NOT NULL,
    "CD_APLICACAO" BIGINT NOT NULL,
    "DT_CADASTRO" TIMESTAMP WITH TIME ZONE NOT NULL,
    "DT_ATUALIZACAO" TIMESTAMP WITH TIME ZONE,
    "CD_USUARIO_CRIACAO" BIGINT NOT NULL,
    "CD_USUARIO_ATUALIZACAO" BIGINT,
    "FL_ATIVO" BOOLEAN NOT NULL
);

-- COMMENT
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT_APLICACAO"."CD_TIPO_GRANT" IS 'CÓDIGO DO GRUPO DO TIPO GRANT APLICAÇÃO';
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT_APLICACAO"."CD_APLICACAO" IS 'CÓDIGO DA APLICAÇÃO DO TIPO GRANT APLICAÇÃO';
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT_APLICACAO"."DT_CADASTRO" IS 'DATA DE CADASTRO DO TIPO GRANT APLICAÇÃO';
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT_APLICACAO"."DT_ATUALIZACAO" IS 'DATA DE ATUALIZAÇÃO DO TIPO GRANT APLICAÇÃO';
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT_APLICACAO"."CD_USUARIO_CRIACAO" IS 'USUÁRIO DE CRIAÇÃO DO TIPO GRANT APLICAÇÃO';
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT_APLICACAO"."CD_USUARIO_ATUALIZACAO" IS 'USUÁRIO DE ATUALIZAÇÃO DO TIPO GRANT APLICAÇÃO';
COMMENT ON COLUMN "OAUTH"."TB_TIPO_GRANT_APLICACAO"."FL_ATIVO" IS '[TRUE/FALSE] - SINALIZAÇÃO DE ATIVO DO TIPO GRANT APLICAÇÃO';

-- PRIMARY KEY 
ALTER TABLE "OAUTH"."TB_TIPO_GRANT_APLICACAO" ADD CONSTRAINT "PK_TB_TIPO_GRANT_APLICACAO" PRIMARY KEY ("CD_TIPO_GRANT", "CD_APLICACAO");

-- FOREIGN KEY
ALTER TABLE "OAUTH"."TB_TIPO_GRANT_APLICACAO" ADD CONSTRAINT "FK_TGAA_TB_TIPO_GRANT_APLICACAO" FOREIGN KEY ("CD_TIPO_GRANT") REFERENCES "OAUTH"."TB_TIPO_GRANT" ("CD_TIPO_GRANT");
ALTER TABLE "OAUTH"."TB_TIPO_GRANT_APLICACAO" ADD CONSTRAINT "FK_TGAB_TB_TIPO_GRANT_APLICACAO" FOREIGN KEY ("CD_APLICACAO") REFERENCES "OAUTH"."TB_APLICACAO" ("CD_APLICACAO"); 
ALTER TABLE "OAUTH"."TB_TIPO_GRANT_APLICACAO" ADD CONSTRAINT "FK_TGAC_TB_TIPO_GRANT_APLICACAO" FOREIGN KEY ("CD_USUARIO_CRIACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO");
ALTER TABLE "OAUTH"."TB_TIPO_GRANT_APLICACAO" ADD CONSTRAINT "FK_TGAD_TB_TIPO_GRANT_APLICACAO" FOREIGN KEY ("CD_USUARIO_ATUALIZACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO"); 

