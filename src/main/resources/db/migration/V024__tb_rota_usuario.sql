-- TABLE
CREATE TABLE IF NOT EXISTS "OAUTH"."TB_ROTA_USUARIO"
(
	"CD_ROTA" BIGINT NOT NULL,
    "CD_USUARIO" BIGINT NOT NULL,
    "DT_CADASTRO" TIMESTAMP WITH TIME ZONE NOT NULL,
    "DT_ATUALIZACAO" TIMESTAMP WITH TIME ZONE,
    "CD_USUARIO_CRIACAO" BIGINT NOT NULL,
    "CD_USUARIO_ATUALIZACAO" BIGINT,
    "FL_ATIVA" BOOLEAN NOT NULL
);

-- COMMENT
COMMENT ON COLUMN "OAUTH"."TB_ROTA_USUARIO"."CD_ROTA" IS 'CÓDIGO DA ROTA DA ROTA USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_ROTA_USUARIO"."CD_USUARIO" IS 'CÓDIGO DO USUÁRIO DA ROTA USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_ROTA_USUARIO"."DT_CADASTRO" IS 'DATA DE CADASTRO DA ROTA USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_ROTA_USUARIO"."DT_ATUALIZACAO" IS 'DATA DE ATUALIZAÇÃO DA ROTA USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_ROTA_USUARIO"."CD_USUARIO_CRIACAO" IS 'USUÁRIO DE CRIAÇÃO DA ROTA USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_ROTA_USUARIO"."CD_USUARIO_ATUALIZACAO" IS 'USUÁRIO DE ATUALIZAÇÃO DA ROTA USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_ROTA_USUARIO"."FL_ATIVA" IS '[TRUE/FALSE] - SINALIZAÇÃO DE ATIVA DA ROTA USUÁRIO';

-- PRIMARY KEY 
ALTER TABLE "OAUTH"."TB_ROTA_USUARIO" ADD CONSTRAINT "PK_TB_ROTA_USUARIO" PRIMARY KEY ("CD_ROTA", "CD_USUARIO");

-- FOREIGN KEY
ALTER TABLE "OAUTH"."TB_ROTA_USUARIO" ADD CONSTRAINT "FK_RUB_TB_ROTA_USUARIO" FOREIGN KEY ("CD_ROTA") REFERENCES "OAUTH"."TB_ROTA" ("CD_ROTA"); 
ALTER TABLE "OAUTH"."TB_ROTA_USUARIO" ADD CONSTRAINT "FK_RUA_TB_ROTA_USUARIO" FOREIGN KEY ("CD_USUARIO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO");
ALTER TABLE "OAUTH"."TB_ROTA_USUARIO" ADD CONSTRAINT "FK_RUC_TB_ROTA_USUARIO" FOREIGN KEY ("CD_USUARIO_CRIACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO");
ALTER TABLE "OAUTH"."TB_ROTA_USUARIO" ADD CONSTRAINT "FK_RUD_TB_ROTA_USUARIO" FOREIGN KEY ("CD_USUARIO_ATUALIZACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO"); 

