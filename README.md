# 👠 CalzaSystem - Sistema Web para la Gestión Comercial v1.0
> **Solución transaccional integral para la gestión retail de Calzados Morales, optimizando el ciclo de inventario, clientes y ventas bajo el patrón MVC.**[cite: 1]

[![Java Version](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.2-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%2523005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)](https://www.thymeleaf.org/)

---

## 📸 Vista Previa del Sistema
*¡Monitoreo comercial y analítico del negocio en tiempo real!*[cite: 1]

| Dashboard Operativo (Vendedor) | Dashboard Gerencial (Administrador) |
|:---:|:---:|
| Análisis de rendimiento semanal, comisión estimada (5%) y producto estrella[cite: 1]. | Control de ingresos diarios, balance de caja y alertas de stock crítico[cite: 1]. |

---

## 🛠️ Arquitectura Técnica (Stack Tecnológico)

El sistema está desarrollado bajo el patrón arquitectónico **Modelo-Vista-Controlador (MVC)**, garantizando una estructura modular y escalable para futuras integraciones[cite: 1].

* **Core & Backend:** Java 17 dentro del entorno Spring Tools Suite (STS) utilizando Spring Boot[cite: 1].
* **Persistencia de Datos:** Spring Data JPA / Hibernate sobre el motor relacional **MySQL Server 8.0**[cite: 1].
* **Capa Analítica:** Lógica compleja y balances automatizados delegados a **Procedimientos Almacenados (Stored Procedures)** optimizados en MySQL[cite: 1].
* **Interfaz de Usuario (Frontend):** Vistas dinámicas con **Thymeleaf**, maquetación responsiva con **Bootstrap 5** y visualización gráfica mediante **Chart.js**[cite: 1].
* **Módulo de Reportes:** 
  * **PDF:** Integración con **JasperReports** para la emisión de comprobantes digitales (Boletas/Facturas)[cite: 1].
  * **Excel:** Exportación del historial completo de transacciones para auditorías administrativas[cite: 1].

---

## 🌟 Funcionalidades Estrella

### 📊 Inteligencia de Negocios & KPIs
- **Segmentación Avanzada:** Gráficos estadísticos que analizan las ventas por género mediante la integración segura de datos de Persona Natural y Jurídica[cite: 1].
- **Métricas del Vendedor:** Panel personalizado con cálculo automático de comisiones, pares vendidos y alertas de productos estancados[cite: 1].

### 🛒 Módulo Transaccional Seguro
- **Integración Logística:** Descuento automático de stock en tiempo real al procesar una venta, manteniendo la consistencia de los datos del almacén[cite: 1].
- **Filtros Avanzados:** Buscador predictivo de existencias por categoría y talla para agilizar la atención en caja[cite: 1].

### 🛡️ Control de Acceso por Roles
El sistema restringe las operaciones críticas mediante autenticación de perfiles bien definidos[cite: 1]:

| Funcionalidad / Módulo | Perfil Administrador 🔑 | Perfil Vendedor 🛒 |
| :--- | :---: | :---: |
| **Dashboard Global e Ingresos** | ✅ Acceso Total[cite: 1] | ❌ Restringido[cite: 1] |
| **Operaciones Almacén (CRUD)** | ✅ Crear, Editar, Eliminar[cite: 1] | 👁️ Solo Lectura (Consultas)[cite: 1] |
| **Módulo Transaccional de Ventas** | ❌ Solo Auditoría | ✅ Procesar Nueva Venta[cite: 1] |
| **Gestión de Clientes (DNI/RUC)** | ✅ Disponible[cite: 1] | ✅ Disponible (Validación AJAX)[cite: 1] |
| **Gestión de Usuarios del Sistema** | ✅ Registro y Control[cite: 1] | ❌ Denegado[cite: 1] |

---

## 🔧 Configuración del Entorno

### 1. Requisitos Previos
* Java Development Kit (JDK) 17.
* MySQL Server 8.0 o superior.
* IDE compatible (Spring Tools Suite recomendado / Eclipse).

### 2. Despliegue de la Base de Datos
1. Crea la base de datos ejecutando el comando:  
   `CREATE DATABASE BDCALZADOS CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`[cite: 1]
2. Ejecuta el script SQL ubicado en la ruta del proyecto para levantar la estructura de tablas, restricciones de llaves foráneas y los procedimientos almacenados[cite: 1].
3. El script incluye un sembrado de datos (seeders) con productos e históricos para pruebas inmediatas[cite: 1].

### 3. Propiedades de Conexión
Configura tus credenciales locales en el archivo `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/BDCALZADOS?serverTimezone=America/Lima
spring.datasource.username=tu_usuario_root
spring.datasource.password=tu_contraseña_mysql
spring.jpa.hibernate.ddl-auto=update
```[cite: 1]

### 🔑 Credenciales de Acceso Rápido (Pruebas)
* **Perfil Administrador:** Usuario: `adminXio` | Contraseña: `123`[cite: 1]
* **Perfil Vendedor:** Usuario: `vendedor` | Contraseña: `456`[cite: 1]

---

## 👥 Equipo de Desarrollo (Grupo N°1)
Proyecto académico desarrollado para la **Escuela de Educación Superior Cibertec** como parte de las **Experiencias Formativas en Situaciones Reales de Trabajo (EFSRT IV)**[cite: 1]:

* **BUSTAMANTE TAPIA, XIOMARA DALESCA** - *Jefa de Proyecto*[cite: 1]
* **JUAREZ TORRES, ANDRES DEMETRIO** - *Analista Funcional*[cite: 1]
* **QUISPE ROMERO, DAYVIS RODRIGO** - *Desarrollador Backend*[cite: 1]
* **MORALES DURAND, JEAN PIERRE** - *Desarrollador Frontend*[cite: 1]

**Docente:** Carlos Israel Accilio Cruz  
*Lima, Perú - Junio de 2026*[cite: 1]
