
Proceso Servidor Balanceador
	Crear socket
	Abrir_canal_de_comunicacion
	Escuchar_Conexion_Cliente
	
	Repetir
	
		Recibir solicitud_cliente
		
		Repetir
			enviar_solicitud_datos_carga
			recibir_datos
		Hasta Que envie_a_todos_los_servidores											
		
		Verificar_servidor_con_menor_carga
		Conectar_con_servidor
		
		Repetir
			enviar_solicitud_cliente
			recibir_datos
		Hasta Que cerrar conexion											
		enviar_datos_a_cliente	
		
	Hasta QueCerrar_canal_de_comunicacion
		
FinProceso
	