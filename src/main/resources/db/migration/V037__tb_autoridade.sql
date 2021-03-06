-- TABLE
CREATE TABLE IF NOT EXISTS "OAUTH"."TB_AUTORIDADE"
(
    "CD_AUTORIDADE" BIGINT NOT NULL,
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
COMMENT ON COLUMN "OAUTH"."TB_AUTORIDADE"."CD_AUTORIDADE" IS 'CÓDIGO DA AUTORIDADE';
COMMENT ON COLUMN "OAUTH"."TB_AUTORIDADE"."NOME" IS 'NOME DA AUTORIDADE';
COMMENT ON COLUMN "OAUTH"."TB_AUTORIDADE"."DESCRICAO" IS 'DESCRIÇÃO DA AUTORIDADE';
COMMENT ON COLUMN "OAUTH"."TB_AUTORIDADE"."DT_CADASTRO" IS 'DATA DE CADASTRO DA AUTORIDADE';
COMMENT ON COLUMN "OAUTH"."TB_AUTORIDADE"."DT_ATUALIZACAO" IS 'DATA DE ATUALIZAÇÃO DA AUTORIDADE';
COMMENT ON COLUMN "OAUTH"."TB_AUTORIDADE"."CD_APLICACAO" IS 'CÓDIGO DA APLICAÇÃO DA AUTORIDADE';
COMMENT ON COLUMN "OAUTH"."TB_AUTORIDADE"."CD_USUARIO_CRIACAO" IS 'CÓDIGO DO USUÁRIO DE CRIAÇÃO DA AUTORIDADE';
COMMENT ON COLUMN "OAUTH"."TB_AUTORIDADE"."CD_USUARIO_ATUALIZACAO" IS 'CÓDIGO DO USUÁRIO DE ATUALIZAÇÃO DA AUTORIDADE';
COMMENT ON COLUMN "OAUTH"."TB_AUTORIDADE"."FL_ATIVA" IS '[TRUE/FALSE] - SINALIZAÇÃO DE ATIVA DA AUTORIDADE';

-- PRIMARY KEY 
ALTER TABLE "OAUTH"."TB_AUTORIDADE" ADD CONSTRAINT "PK_TB_AUTORIDADE" PRIMARY KEY ("CD_AUTORIDADE");

-- UNIQUE KEY 
ALTER TABLE "OAUTH"."TB_AUTORIDADE" ADD CONSTRAINT "UK_NCA_TB_AUTORIDADE" UNIQUE ("NOME", "CD_APLICACAO");

-- FOREIGN KEY
ALTER TABLE "OAUTH"."TB_AUTORIDADE" ADD CONSTRAINT "FK_AA_TB_APLICACAO" FOREIGN KEY ("CD_APLICACAO") REFERENCES "OAUTH"."TB_APLICACAO" ("CD_APLICACAO");
ALTER TABLE "OAUTH"."TB_AUTORIDADE" ADD CONSTRAINT "FK_AB_TB_USUARIO" FOREIGN KEY ("CD_USUARIO_CRIACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO");
ALTER TABLE "OAUTH"."TB_AUTORIDADE" ADD CONSTRAINT "FK_AC_TB_USUARIO" FOREIGN KEY ("CD_USUARIO_ATUALIZACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO"); 

