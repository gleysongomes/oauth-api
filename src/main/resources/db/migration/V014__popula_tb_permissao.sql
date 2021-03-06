-- INSERT - USUÁRIO
INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (1, 'LISTAR_USUARIO', 'Permite listar usuários.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (2, 'BUSCAR_USUARIO', 'Permite buscar usuário.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (3, 'ADICIONAR_USUARIO', 'Permite adicionar usuário.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (4, 'ATUALIZAR_USUARIO', 'Permite atualizar usuário.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (5, 'EXCLUIR_USUARIO', 'Permite excluir usuário.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (6, 'RELATORIO_USUARIO', 'Permite visualizar relatório de usuários.', now()::timestamp(0), 2, 1, TRUE);

-- INSERT - GRUPO
INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (7, 'LISTAR_GRUPO', 'Permite listar grupos.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (8, 'BUSCAR_GRUPO', 'Permite buscar grupo.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (9, 'ADICIONAR_GRUPO', 'Permite adicionar grupo.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (10, 'ATUALIZAR_GRUPO', 'Permite atualizar grupo.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (11, 'EXCLUIR_GRUPO', 'Permite excluir grupo.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (12, 'RELATORIO_GRUPO', 'Permite visualizar relatório de grupos.', now()::timestamp(0), 2, 1, TRUE);

-- INSERT - PERMISSÃO
INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (13, 'LISTAR_PERMISSAO', 'Permite listar permissões.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (14, 'BUSCAR_PERMISSAO', 'Permite buscar permissão.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (15, 'ADICIONAR_PERMISSAO', 'Permite adicionar permissão.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (16, 'ATUALIZAR_PERMISSAO', 'Permite atualizar permissão.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (17, 'EXCLUIR_PERMISSAO', 'Permite excluir permissão.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (18, 'RELATORIO_PERMISSAO', 'Permite visualizar relatório de permissões.', now()::timestamp(0), 2, 1, TRUE);

-- INSERT - APLICAÇÃO
INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (19, 'LISTAR_APLICACAO', 'Permite listar aplicações.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (20, 'BUSCAR_APLICACAO', 'Permite buscar aplicação.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (21, 'ADICIONAR_APLICACAO', 'Permite adicionar aplicação.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (22, 'ATUALIZAR_APLICACAO', 'Permite atualizar aplicação.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (23, 'EXCLUIR_APLICACAO', 'Permite excluir aplicação.', now()::timestamp(0), 2, 1, TRUE);

INSERT INTO "OAUTH"."TB_PERMISSAO"(
	"CD_PERMISSAO", 
	"NOME", 
	"DESCRICAO", 
	"DT_CADASTRO", 
	"CD_APLICACAO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVA")
VALUES (24, 'RELATORIO_APLICACAO', 'Permite visualizar relatório de aplicações.', now()::timestamp(0), 2, 1, TRUE);

