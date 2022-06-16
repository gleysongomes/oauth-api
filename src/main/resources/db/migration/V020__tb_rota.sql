-- TABLE
CREATE TABLE IF NOT EXISTS "OAUTH"."TB_ROTA"
(
    "CD_ROTA" BIGINT NOT NULL,
    "NOME" CHARACTER VARYING(100) NOT NULL,
    "DESCRICAO" CHARACTER VARYING(100) NOT NULL,
    "DT_CADASTRO" TIMESTAMP WITH TIME ZONE NOT NULL,
    "DT_ATUALIZACAO" TIMESTAMP WITH TIME ZONE,
    "CD_APLICACAO" BIGINT NOT NULL,
    "CD_USUARIO_CRIACAO" BIGINT NOT NULL,
    "CD_USUARIO_ATUALIZACAO" BIGINT,
    "FL_ATIVA" BOOLEAN NOT NULL
);

-- COMMENT
COMMENT ON COLUMN "OAUTH"."TB_ROTA"."CD_ROTA" IS 'CÓDIGO DA ROTA';
COMMENT ON COLUMN "OAUTH"."TB_ROTA"."NOME" IS 'NOME DA ROTA';
COMMENT ON COLUMN "OAUTH"."TB_ROTA"."DESCRICAO" IS 'DESCRIÇÃO DA ROTA';
COMMENT ON COLUMN "OAUTH"."TB_ROTA"."DT_CADASTRO" IS 'DATA DE CADASTRO DA ROTA';
COMMENT ON COLUMN "OAUTH"."TB_ROTA"."DT_ATUALIZACAO" IS 'DATA DE ATUALIZAÇÃO DA ROTA';
COMMENT ON COLUMN "OAUTH"."TB_ROTA"."CD_APLICACAO" IS 'CÓDIGO DA APLICAÇÃO DA ROTA';
COMMENT ON COLUMN "OAUTH"."TB_ROTA"."CD_USUARIO_CRIACAO" IS 'CÓDIGO DO USUÁRIO DE CRIAÇÃO DA ROTA';
COMMENT ON COLUMN "OAUTH"."TB_ROTA"."CD_USUARIO_ATUALIZACAO" IS 'CÓDIGO DO USUÁRIO DE ATUALIZAÇÃO DA ROTA';
COMMENT ON COLUMN "OAUTH"."TB_ROTA"."FL_ATIVA" IS '[TRUE/FALSE] - SINALIZAÇÃO DE ATIVA DA ROTA';

-- PRIMARY KEY 
ALTER TABLE "OAUTH"."TB_ROTA" ADD CONSTRAINT "PK_TB_ROTA" PRIMARY KEY ("CD_ROTA");

-- UNIQUE KEY 
ALTER TABLE "OAUTH"."TB_ROTA" ADD CONSTRAINT "UK_NCA_TB_ROTA" UNIQUE ("NOME", "CD_APLICACAO");

-- FOREIGN KEY
ALTER TABLE "OAUTH"."TB_ROTA" ADD CONSTRAINT "FK_RA_TB_APLICACAO" FOREIGN KEY ("CD_APLICACAO") REFERENCES "OAUTH"."TB_APLICACAO" ("CD_APLICACAO");
ALTER TABLE "OAUTH"."TB_ROTA" ADD CONSTRAINT "FK_RB_TB_USUARIO" FOREIGN KEY ("CD_USUARIO_CRIACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO");
ALTER TABLE "OAUTH"."TB_ROTA" ADD CONSTRAINT "FK_RC_TB_USUARIO" FOREIGN KEY ("CD_USUARIO_ATUALIZACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO"); 
