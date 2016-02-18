Proceso Servidor 
	Crear socket
	Abrir_canal_de_comunicacion
	Escuchar_Conexion_Balanceador
	
	Si peticion=carga Entonces
		enviar_datos_carga
	Sino
		Repetir
			Recibir solicitudes_balanceador
			Procesar_solicitud_balanceador
			enviar_datos
		Hasta Que cerrar conexion											
	Fin Si
		
FinProceso
