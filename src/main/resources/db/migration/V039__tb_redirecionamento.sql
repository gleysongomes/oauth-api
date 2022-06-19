-- TABLE
CREATE TABLE IF NOT EXISTS "OAUTH"."TB_REDIRECIONAMENTO"
(
    "CD_REDIRECIONAMENTO" BIGINT NOT NULL,
    "URL" CHARACTER VARYING(100) NOT NULL,
    "DESCRICAO" CHARACTER VARYING(100) NOT NULL,
    "DT_CADASTRO" TIMESTAMP WITH TIME ZONE NOT NULL,
    "DT_ATUALIZACAO" TIMESTAMP WITH TIME ZONE,
    "CD_APLICACAO" BIGINT NOT NULL,
    "CD_USUARIO_CRIACAO" BIGINT NOT NULL,
    "CD_USUARIO_ATUALIZACAO" BIGINT,
    "FL_ATIVO" BOOLEAN NOT NULL
);

-- COMMENT
COMMENT ON COLUMN "OAUTH"."TB_REDIRECIONAMENTO"."CD_REDIRECIONAMENTO" IS 'CÓDIGO DO REDIRECIONAMENTO';
COMMENT ON COLUMN "OAUTH"."TB_REDIRECIONAMENTO"."URL" IS 'URL DO REDIRECIONAMENTO';
COMMENT ON COLUMN "OAUTH"."TB_REDIRECIONAMENTO"."DESCRICAO" IS 'DESCRIÇÃO DO REDIRECIONAMENTO';
COMMENT ON COLUMN "OAUTH"."TB_REDIRECIONAMENTO"."DT_CADASTRO" IS 'DATA DE CADASTRO DO REDIRECIONAMENTO';
COMMENT ON COLUMN "OAUTH"."TB_REDIRECIONAMENTO"."DT_ATUALIZACAO" IS 'DATA DE ATUALIZAÇÃO DO REDIRECIONAMENTO';
COMMENT ON COLUMN "OAUTH"."TB_REDIRECIONAMENTO"."CD_APLICACAO" IS 'CÓDIGO DA APLICAÇÃO DO REDIRECIONAMENTO';
COMMENT ON COLUMN "OAUTH"."TB_REDIRECIONAMENTO"."CD_USUARIO_CRIACAO" IS 'CÓDIGO DO USUÁRIO DE CRIAÇÃO DO REDIRECIONAMENTO';
COMMENT ON COLUMN "OAUTH"."TB_REDIRECIONAMENTO"."CD_USUARIO_ATUALIZACAO" IS 'CÓDIGO DO USUÁRIO DE ATUALIZAÇÃO DO REDIRECIONAMENTO';
COMMENT ON COLUMN "OAUTH"."TB_REDIRECIONAMENTO"."FL_ATIVO" IS '[TRUE/FALSE] - SINALIZAÇÃO DE ATIVO DO REDIRECIONAMENTO';

-- PRIMARY KEY 
ALTER TABLE "OAUTH"."TB_REDIRECIONAMENTO" ADD CONSTRAINT "PK_TB_REDIRECIONAMENTO" PRIMARY KEY ("CD_REDIRECIONAMENTO");

-- UNIQUE KEY 
ALTER TABLE "OAUTH"."TB_REDIRECIONAMENTO" ADD CONSTRAINT "UK_UCA_TB_REDIRECIONAMENTO" UNIQUE ("URL", "CD_APLICACAO");

-- FOREIGN KEY
ALTER TABLE "OAUTH"."TB_REDIRECIONAMENTO" ADD CONSTRAINT "FK_RA_TB_APLICACAO" FOREIGN KEY ("CD_APLICACAO") REFERENCES "OAUTH"."TB_APLICACAO" ("CD_APLICACAO");
ALTER TABLE "OAUTH"."TB_REDIRECIONAMENTO" ADD CONSTRAINT "FK_RB_TB_USUARIO" FOREIGN KEY ("CD_USUARIO_CRIACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO");
ALTER TABLE "OAUTH"."TB_REDIRECIONAMENTO" ADD CONSTRAINT "FK_RC_TB_USUARIO" FOREIGN KEY ("CD_USUARIO_ATUALIZACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO"); 
