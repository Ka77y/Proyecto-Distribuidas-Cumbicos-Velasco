Proceso Servidor Balanceador
	Proceso Crear socket
	Proceso Abrir_canal_de_comunicacion
	Proceso Escuchar_Conexion
		Repetir
			Proceso recibir solicitudes_servidor
				Proceso Conectar_con_servidor
				Repetir
					Proceso enviar datos
					Proceso recibir datos
				Hasta Que cerrar conexion
				
			Proceso recibir solicitudes_cliente
				Proceso Conectar_con_cliente
				Repetir
					Proceso enviar datos
						Proceso recibir datos
				Hasta Que cerrar conexion
					
		Hasta Que cerrar conexion
	
	Proceso Cerrar_canal_de_comunicacion
FinProceso
	