-- INSERT
INSERT INTO "OAUTH"."TB_GRUPO"(
	"CD_GRUPO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVO")
VALUES (1, 'ADMIN', 'Administrador do sistema.', now()::timestamp(0), 1, TRUE);

