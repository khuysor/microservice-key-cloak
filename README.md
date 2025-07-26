# Microservice Project Structure

This is a multi-module Maven project for microservices with a proper parent-child hierarchy.

## Project Structure

```
backend/
├── pom.xml                    (Root parent POM - coordinates all modules)
├── common/
│   ├── pom.xml               (Common parent POM - shared configurations)
│   └── src/                  (Common utilities and configurations)
├── api-product/
│   ├── pom.xml               (Product API service)
│   └── src/                  (Product service implementation)
└── keycloak-admin/
    ├── pom.xml               (Keycloak admin service)
    └── src/                  (Keycloak admin implementation)
```

## Maven Hierarchy

1. **Root Parent POM** (`/pom.xml`)
   - Coordinates all modules via `<modules>` section
   - Manages all dependency versions via `<dependencyManagement>`
   - Provides common plugin configurations
   - No Spring Boot parent dependency (manages Spring Boot via BOM)

2. **Common Module** (`/common/pom.xml`)
   - Inherits from root parent
   - Contains shared utilities, configurations, and common dependencies
   - Can be used as a dependency by other services

3. **Service Modules** (`/api-product/pom.xml`, `/keycloak-admin/pom.xml`)
   - Inherit from root parent
   - Can depend on the common module
   - Only declare dependencies they specifically need

## How to Build

### Build Everything
```bash
# From the root directory
mvn clean install
```

### Build Specific Module
```bash
# From the root directory
mvn clean install -pl api-product

# Or from the module directory
cd api-product
mvn clean install
```

### Run a Service
```bash
# From the module directory
cd api-product
mvn spring-boot:run
```

## Benefits of This Structure

1. **Centralized Dependency Management**: All versions managed in one place
2. **Consistent Configuration**: All modules use the same Java version, encoding, plugin versions
3. **Shared Code**: Common utilities in the common module
4. **Easy Build**: Single command builds everything
5. **Independent Deployment**: Each service can be built and deployed independently
6. **Version Consistency**: All modules inherit the same version from parent

## Adding a New Service

1. Create a new directory under `/backend/`
2. Add the directory name to `<modules>` in root `pom.xml`
3. Create a `pom.xml` with parent reference:

```xml
<parent>
    <groupId>com.huysor.saas</groupId>
    <artifactId>microservice-parent</artifactId>
    <version>0.0.1</version>
    <relativePath>../pom.xml</relativePath>
</parent>
```

4. Add dependencies as needed (versions are managed by parent)

## Example Service Dependencies

```xml
<dependencies>
    <!-- Common module (optional) -->
    <dependency>
        <groupId>com.huysor.saas</groupId>
        <artifactId>common-parent</artifactId>
    </dependency>
    
    <!-- Spring Boot starters (no version needed) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Database (version managed by parent) -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```
