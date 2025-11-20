
# 💱 Conversor de Monedas — Java + Gson + Swing

Aplicación de **conversión de divisas** desarrollada en **Java 11+**, utilizando:

* **HttpClient / HttpRequest / HttpResponse** para interactuar con la [ExchangeRate-API](https://www.exchangerate-api.com/).
* **Gson** para el manejo y análisis de datos en formato **JSON**.
* **Swing** para una interfaz gráfica moderna, intuitiva y funcional.

Convierte fácilmente entre **USD, ARS, BRL, CLP, COP y BOB**, consulta tasas actualizadas y lleva un **historial de conversiones** en tiempo real.

---

[![Repo](https://img.shields.io/badge/GitHub-%40TodTete-blue?logo=github)](https://github.com/TodTete)
![Java](https://img.shields.io/badge/Java-11%2B-orange?logo=coffeescript)
![Maven](https://img.shields.io/badge/Maven-3.8%2B-red?logo=apache-maven)
![Gson](https://img.shields.io/badge/Gson-2.11.0-green)
![Swing](https://img.shields.io/badge/Swing-UI-lightgrey)
![Status](https://img.shields.io/badge/status-completo-success)

---

## 🪟 Interfaz Gráfica

La aplicación incluye una **GUI construida con Swing**, que permite:

* Seleccionar **moneda origen y destino** desde menús desplegables.
* Ingresar un monto numérico y realizar la conversión en un clic.
* Visualizar el **resultado** y la **tasa de cambio** actual.
* Consultar el **historial de conversiones**, con marcas de tiempo.

### ✨ Características visuales

* **Diseño limpio y centrado**.
* **Etiquetas claras** y mensajes de error manejados con `JOptionPane`.
* Ejecución asíncrona con `SwingWorker` (sin bloquear la interfaz).
* Totalmente funcional sin dependencias adicionales.

---

## ⚙️ Requisitos

* **Java 11+**
* **Maven 3.8+**
* **Conexión a internet**
* **Clave API** gratuita de [ExchangeRate-API](https://www.exchangerate-api.com/)

---

## 📦 Instalación y configuración

### 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/TodTete/conversor-monedas.git
cd conversor-monedas
```

### 2️⃣ Configurar tu API key

Crea una cuenta en [ExchangeRate-API](https://www.exchangerate-api.com/).
Luego guarda tu clave en una variable de entorno:

**Windows (PowerShell):**

```powershell
setx EXCHANGE_RATE_API_KEY "tu_api_key_aqui"
```

**macOS / Linux:**

```bash
export EXCHANGE_RATE_API_KEY=tu_api_key_aqui
```

---

## ▶️ Ejecución

### 🖥️ Modo consola

```bash
mvn -q exec:java -Dexec.mainClass=lad.com.alura.conversormoneda.ConversorApp
```

### 🪟 Modo gráfico (GUI)

```bash
mvn -q exec:java -Dexec.mainClass=lad.com.alura.conversormoneda.GuiApp
```

---

## 🧱 Estructura del proyecto

```
conversor-monedas/
├─ pom.xml
└─ src/
   └─ main/
      └─ java/
         └─ lad/com/alura/conversormoneda/
            ├─ ConversorApp.java         # Interfaz por consola
            ├─ GuiApp.java               # Interfaz gráfica Swing
            ├─ ConverterFrame.java       # Ventana principal
            ├─ Conversor.java            # Lógica del menú e interacción
            ├─ ExchangeRateClient.java   # Cliente HTTP para la API
            ├─ CurrencyCodes.java        # Lista de códigos soportados
            └─ HistoryItem.java          # Modelo de historial
```

---

## 💡 Funcionalidades

* 🌐 **Consulta de tasas en tiempo real** desde ExchangeRate-API.
* 💵 Conversión entre las monedas más usadas de LATAM.
* 🧾 **Historial** persistente en memoria durante la sesión.
* ⚡ **Interfaz gráfica responsiva** y asincrónica (sin congelamientos).
* 🧩 Código modular, legible y extensible.

---

## 🗺️ Roadmap (mejoras futuras)

* [ ] Guardar el historial de conversiones en archivo local (`JSON` o `CSV`).
* [ ] Soporte para más monedas internacionales.
* [ ] Opción de cambiar moneda base predeterminada.
* [ ] Diseño visual con **JavaFX** y temas personalizados.
* [ ] Integración con APIs adicionales de tasas.

---

## 👤 Autor

**Ricardo Vallejo Sanchez @TodTete**
💼 Proyecto académico y demostrativo — Alura / Java Challenge

---

## 🧾 Licencia

Este proyecto se distribuye bajo la licencia **MIT**.
Consulta [`LICENSE`](LICENSE) para más información.

---

### 🏷️ Tags

`java` · `maven` · `gson` · `swing` · `api` · `exchange-rate` · `monedas` · `currency-converter` · `httpclient` · `json` · `alura`

---
