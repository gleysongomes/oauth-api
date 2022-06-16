-- INSERT
INSERT INTO "OAUTH"."TB_TIPO_GRANT"(
	"CD_TIPO_GRANT", 
	"NOME",  
	"DT_CADASTRO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVO")
VALUES (1, 
	'password', 
	now()::timestamp(0), 
	1, 
	TRUE
);

INSERT INTO "OAUTH"."TB_TIPO_GRANT"(
	"CD_TIPO_GRANT", 
	"NOME",  
	"DT_CADASTRO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVO")
VALUES (2, 
	'refresh_token', 
	now()::timestamp(0), 
	1, 
	TRUE
);

INSERT INTO "OAUTH"."TB_TIPO_GRANT"(
	"CD_TIPO_GRANT", 
	"NOME",  
	"DT_CADASTRO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVO")
VALUES (3, 
	'authorization_code', 
	now()::timestamp(0), 
	1, 
	TRUE
);

INSERT INTO "OAUTH"."TB_TIPO_GRANT"(
	"CD_TIPO_GRANT", 
	"NOME",  
	"DT_CADASTRO", 
	"CD_USUARIO_CRIACAO", 
	"FL_ATIVO")
VALUES (4, 
	'client_credentials', 
	now()::timestamp(0), 
	1, 
	TRUE
);

