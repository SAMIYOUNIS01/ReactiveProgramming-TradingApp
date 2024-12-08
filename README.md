# 📈 ReactiveProgramming-TradingApp

A **Spring WebFlux** application demonstrating reactive programming principles, designed for trading actions like **buying** and **selling** shares. The application consists of three microservices:

- **Aggregator Service**: Gateway for frontend interaction.
- **Customer Service**: Manages user data and validates trades.
- **External Service**: Streams real-time product price data.

---

## 🚀 Features

### Aggregator Service
- **Get Customer Information**: Retrieves customer details from the Customer Service.
- **Stream Product Prices**: Streams real-time price data from the External Service.
- **Trade (Buy/Sell)**: Handles trade requests by:
  - Fetching the current price from the External Service.
  - Validating trade eligibility.
  - Updating customer information in the database.

### Customer Service
- **Validates Trade Requests**:
  - Ensures sufficient balance for **buy** actions.
  - Verifies sufficient shares for **sell** actions.
- **Updates User Information**: Adjusts balances and shares after successful trades.
- **Stores Trade Data**: Persists trade records in the database.

### External Service
- Provides real-time product price streaming for trading purposes.

---

## 🏗️ Architecture

The system follows a **microservices architecture** with the following flow:

1. **Frontend** ➡️ **Aggregator Service**: Processes user requests.
2. **Aggregator Service** ➡️ **External Service**: Streams product prices.
3. **Aggregator Service** ➡️ **Customer Service**: Manages customer data and processes trades.

### 🛠️ Technology Stack
- **Backend Framework**: Spring WebFlux
- **Reactive Programming**: Project Reactor, Reactive Streams
- **Database**: (H2)
- **Streaming**: Reactive Streams API for price streaming

---

## ⚡ Getting Started

### Prerequisites
- **Java 17** or later
- **Maven**
- **Docker** (optional for containerized deployment)

### Steps to Run Locally

   git clone https://github.com/username/ReactiveProgramming-TradingApp.git
   cd ReactiveProgramming-TradingApp
   run the jar file (java -jar SpringWebFlux-ExternalSerivce.jar)
   run the customer and the aggregator serivce
   and access the UI by http://localhost:8080
   8080 --> aggregator service
   6060 --> customer service 
   
