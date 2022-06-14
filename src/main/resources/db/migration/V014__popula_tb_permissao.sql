-- INSERT
INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (1, 'ADICIONAR_PERMISSAO', 'Permite adição de permissão.', now()::timestamp(0), 1, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (2, 'ATUALIZAR_PERMISSAO', 'Permite atualização de permissão.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (3, 'EXCLUIR_PERMISSAO', 'Permite exclusão de permissão.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (4, 'VISUALIZAR_PERMISSAO', 'Permite visualização de permissão.', now()::timestamp(0), 1, 1, TRUE);

