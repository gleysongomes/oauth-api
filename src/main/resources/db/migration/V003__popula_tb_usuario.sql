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
VALUES (1, 'admin', 'admin@localhost', 'Admin', '$2a$10$iOqf61qETMrvS5n7VgUnUOHx4IdqD4L/XfxmThBe9P2XB.XNLQ6bW', now()::timestamp(0), 1, TRUE);

