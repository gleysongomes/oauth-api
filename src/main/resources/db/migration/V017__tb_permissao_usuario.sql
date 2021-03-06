-- TABLE
CREATE TABLE IF NOT EXISTS "OAUTH"."TB_PERMISSAO_USUARIO"
(
	"CD_PERMISSAO" BIGINT NOT NULL,
    "CD_USUARIO" BIGINT NOT NULL,
    "DT_CADASTRO" TIMESTAMP WITH TIME ZONE NOT NULL,
    "DT_ATUALIZACAO" TIMESTAMP WITH TIME ZONE,
    "CD_USUARIO_CRIACAO" BIGINT NOT NULL,
    "CD_USUARIO_ATUALIZACAO" BIGINT,
    "FL_ATIVA" BOOLEAN NOT NULL
);

-- COMMENT
COMMENT ON COLUMN "OAUTH"."TB_PERMISSAO_USUARIO"."CD_PERMISSAO" IS 'CÓDIGO DA PERMISSÃO DA PERMISSÃO USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_PERMISSAO_USUARIO"."CD_USUARIO" IS 'CÓDIGO DO USUÁRIO DA PERMISSÃO USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_PERMISSAO_USUARIO"."DT_CADASTRO" IS 'DATA DE CADASTRO DA PERMISSÃO USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_PERMISSAO_USUARIO"."DT_ATUALIZACAO" IS 'DATA DE ATUALIZAÇÃO DA PERMISSÃO USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_PERMISSAO_USUARIO"."CD_USUARIO_CRIACAO" IS 'USUÁRIO DE CRIAÇÃO DA PERMISSÃO USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_PERMISSAO_USUARIO"."CD_USUARIO_ATUALIZACAO" IS 'USUÁRIO DE ATUALIZAÇÃO DA PERMISSÃO USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_PERMISSAO_USUARIO"."FL_ATIVA" IS '[TRUE/FALSE] - SINALIZAÇÃO DE ATIVA DA PERMISSÃO USUÁRIO';

-- PRIMARY KEY 
ALTER TABLE "OAUTH"."TB_PERMISSAO_USUARIO" ADD CONSTRAINT "PK_TB_PERMISSAO_USUARIO" PRIMARY KEY ("CD_PERMISSAO", "CD_USUARIO");

-- FOREIGN KEY
ALTER TABLE "OAUTH"."TB_PERMISSAO_USUARIO" ADD CONSTRAINT "FK_PUB_TB_PERMISSAO_USUARIO" FOREIGN KEY ("CD_PERMISSAO") REFERENCES "OAUTH"."TB_PERMISSAO" ("CD_PERMISSAO"); 
ALTER TABLE "OAUTH"."TB_PERMISSAO_USUARIO" ADD CONSTRAINT "FK_PUA_TB_PERMISSAO_USUARIO" FOREIGN KEY ("CD_USUARIO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO");
ALTER TABLE "OAUTH"."TB_PERMISSAO_USUARIO" ADD CONSTRAINT "FK_PUC_TB_PERMISSAO_USUARIO" FOREIGN KEY ("CD_USUARIO_CRIACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO");
ALTER TABLE "OAUTH"."TB_PERMISSAO_USUARIO" ADD CONSTRAINT "FK_PUD_TB_PERMISSAO_USUARIO" FOREIGN KEY ("CD_USUARIO_ATUALIZACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO"); 

