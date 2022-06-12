-- TABLE
CREATE TABLE IF NOT EXISTS "OAUTH"."TB_GRUPO_USUARIO"
(
    "CD_GRUPO" BIGINT NOT NULL,
    "CD_USUARIO" BIGINT NOT NULL,
    "DT_CADASTRO" TIMESTAMP WITH TIME ZONE NOT NULL,
    "DT_ATUALIZACAO" TIMESTAMP WITH TIME ZONE,
    "CD_USUARIO_CRIACAO" BIGINT NOT NULL,
    "CD_USUARIO_ATUALIZACAO" BIGINT,
    "FL_ATIVO" BOOLEAN NOT NULL
);

-- COMMENT
COMMENT ON COLUMN "OAUTH"."TB_GRUPO_USUARIO"."CD_GRUPO" IS 'CÓDIGO DO GRUPO DO GRUPO USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_GRUPO_USUARIO"."CD_USUARIO" IS 'CÓDIGO DO USUÁRIO DO GRUPO USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_GRUPO_USUARIO"."DT_CADASTRO" IS 'DATA DE CADASTRO DO GRUPO USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_GRUPO_USUARIO"."DT_ATUALIZACAO" IS 'DATA DE ATUALIZAÇÃO DO GRUPO USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_GRUPO_USUARIO"."CD_USUARIO_CRIACAO" IS 'USUÁRIO DE CRIAÇÃO DO GRUPO USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_GRUPO_USUARIO"."CD_USUARIO_ATUALIZACAO" IS 'USUÁRIO DE ATUALIZAÇÃO DO GRUPO USUÁRIO';
COMMENT ON COLUMN "OAUTH"."TB_GRUPO_USUARIO"."FL_ATIVO" IS '[TRUE/FALSE] - SINALIZAÇÃO DE ATIVO DO GRUPO USUÁRIO';

-- PRIMARY KEY 
ALTER TABLE "OAUTH"."TB_GRUPO_USUARIO" ADD CONSTRAINT "PK_TB_GRUPO_USUARIO" PRIMARY KEY ("CD_GRUPO", "CD_USUARIO");

-- FOREIGN KEY
ALTER TABLE "OAUTH"."TB_GRUPO_USUARIO" ADD CONSTRAINT "FK_GUA_TB_GRUPO_USUARIO" FOREIGN KEY ("CD_GRUPO") REFERENCES "OAUTH"."TB_GRUPO" ("CD_GRUPO");
ALTER TABLE "OAUTH"."TB_GRUPO_USUARIO" ADD CONSTRAINT "FK_GUB_TB_GRUPO_USUARIO" FOREIGN KEY ("CD_USUARIO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO"); 
ALTER TABLE "OAUTH"."TB_GRUPO_USUARIO" ADD CONSTRAINT "FK_GUC_TB_GRUPO_USUARIO" FOREIGN KEY ("CD_USUARIO_CRIACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO");
ALTER TABLE "OAUTH"."TB_GRUPO_USUARIO" ADD CONSTRAINT "FK_GUD_TB_GRUPO_USUARIO" FOREIGN KEY ("CD_USUARIO_ATUALIZACAO") REFERENCES "OAUTH"."TB_USUARIO" ("CD_USUARIO"); 
