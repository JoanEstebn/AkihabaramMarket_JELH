/**
 * Modelo principal del cliente.
 * Autor: Joan Esteban Londoño Hernández
 * Versión: 1.0.0.0 - 13/06/2025
 */

package model;

import java.time.LocalDate;

public class Cliente {
	private int id;
	private String nombre;
	private String email;
	private String telefono;
	private LocalDate fechaRegistro;

	// Constructor sin ID (para inserciones)
	public Cliente(String nombre, String email, String telefono) {
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
	}

	// Constructor completo (para recuperaciones)
	public Cliente(int id, String nombre, String email, String telefono, LocalDate fechaRegistro) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.fechaRegistro = fechaRegistro;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", email=" + email + ", telefono=" + telefono
				+ ", fechaRegistro=" + fechaRegistro + "]";
	}
	
	
}