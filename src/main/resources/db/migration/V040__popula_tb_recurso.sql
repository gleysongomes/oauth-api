-- INSERT
INSERT INTO "OAUTH"."TB_RECURSO"(
	"CD_RECURSO", 
	"NOME", 
	"DESCRICAO", 
	"CD_APLICACAO", 
	"DT_CADASTRO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVO")
VALUES (
	1, 
	'oauth2-resource', 
	'Servidor de recursos.', 
	1, 
	now()::timestamp(0), 
	1, 
	TRUE
);

