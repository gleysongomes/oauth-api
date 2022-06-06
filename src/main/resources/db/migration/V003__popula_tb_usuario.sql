-- INSERT
INSERT INTO "OAUTH"."TB_USUARIO"(
	"CD_USUARIO", 
	"LOGIN", 
	"EMAIL", 
	"NOME", 
	"HASH_SENHA", 
	"DT_CADASTRO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVO")
VALUES (1, 'admin', 'admin@localhost', 'Admin', 'a', now()::timestamp(0), 1, TRUE);

