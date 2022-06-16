-- INSERT
INSERT INTO "OAUTH"."TB_ROTA"(
	"CD_ROTA", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (1, '/usuarios', 'Rota de usuários.', now()::timestamp(0), 1, 1, TRUE);

INSERT INTO "OAUTH"."TB_ROTA"(
	"CD_ROTA", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (2, '/grupos', 'Rota de grupos.', now()::timestamp(0), 1, 1, TRUE);

