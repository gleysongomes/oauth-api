-- INSERT
INSERT INTO "OAUTH"."TB_ROTA_GRUPO"(
	"CD_ROTA", 
	"CD_GRUPO", 
	"DT_CADASTRO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (1, 1, now()::timestamp(0), 1, TRUE);

INSERT INTO "OAUTH"."TB_ROTA_GRUPO"(
	"CD_ROTA", 
	"CD_GRUPO", 
	"DT_CADASTRO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (2, 1, now()::timestamp(0), 1, TRUE);

