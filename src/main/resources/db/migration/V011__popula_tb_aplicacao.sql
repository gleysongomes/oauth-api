-- INSERT
INSERT INTO "OAUTH"."TB_APLICACAO"(
	"CD_APLICACAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA", 
	"TP_APLICACAO", 
	"SEGREDO", 
	"FL_SEGURANCA", 
	"TMP_ACCESS_TOKEN", 
	"TMP_REFRESH_TOKEN", 
	"FL_REFRESH_TOKEN")
VALUES (
	1, 
	'OAUTH_CLI', 
	'Aplicação front-end do servidor oauth2.', 
	now()::timestamp(0), 
	1, 
	TRUE, 
	'FRONT_END', 
	'a', 
	TRUE, 
	86400, 
	300, 
	TRUE
);

INSERT INTO "OAUTH"."TB_APLICACAO"(
	"CD_APLICACAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA", 
	"TP_APLICACAO", 
	"SEGREDO", 
	"FL_SEGURANCA", 
	"TMP_ACCESS_TOKEN",  
	"FL_REFRESH_TOKEN")
VALUES (
	2, 
	'OAUTH_API', 
	'Api do servidor oauth2.', 
	now()::timestamp(0), 
	1, 
	TRUE, 
	'BACK_END', 
	'b', 
	FALSE, 
	0, 
	FALSE
);

