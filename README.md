# Portfolio - Quarkus Kotlin with Azure Infrastructure

This project is a modern portfolio website built with Quarkus and Kotlin, deployed to Azure with a multi-environment setup (development, test, production).

## Azure Infrastructure Setup

This project uses Terraform to provision and manage the following Azure resources:

- **App Services**: Hosting the Quarkus application on `yousef.code` (production), `test.yousef.code` (test), and `dev.yousef.code` (development)
- **PostgreSQL Servers**: Database for each environment
- **Key Vaults**: Managing secrets and JWT certificates
- **Storage Accounts**: For storing assets and blobs

### Setting Up Azure Environment

Before deploying to Azure, you need to:

1. Configure your Azure CLI:
   ```shell
   az login
   az account set --subscription "Your-Subscription-ID"
   ```

2. Navigate to the Terraform directory and initialize:
   ```shell
   cd terraform
   terraform init
   ```

3. Apply the Terraform configuration:
   ```shell
   terraform plan -out=tfplan
   terraform apply tfplan
   ```

4. Set up JWT certificates for each environment:
   ```shell
   ./jwt-setup.sh dev
   ./jwt-setup.sh test
   ./jwt-setup.sh prod
   ```

## CI/CD Pipeline

The project includes an Azure DevOps pipeline configuration in `azure-pipelines.yml` which:

- Builds the application
- Runs tests
- Deploys to the appropriate environment based on the branch
- Configures custom domains and SSL

## Local Development

### Running in Dev Mode

For local development, a Docker Compose setup is provided:

1. Start the PostgreSQL database:
   ```shell
   docker-compose up -d
   ```

2. Generate JWT certificates for development:
   ```shell
   docker-compose --profile setup up jwt-generator
   ```

3. Run the application in dev mode:
   ```shell
   ./gradlew quarkusDev
   ```

The application will be available at <http://localhost:8080/> with a Dev UI at <http://localhost:8080/q/dev/>.

### Database Management

An optional pgAdmin interface is available:

```shell
docker-compose --profile dev-tools up -d
```

Access pgAdmin at <http://localhost:5050/> with:
- Email: admin@admin.com
- Password: admin

## Packaging and Deployment

### Creating a JAR

```shell
./gradlew build
```

This produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.

### Building an Über-JAR

```shell
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

### Creating a Native Executable

```shell
./gradlew build -Dquarkus.native.enabled=true
```

Or using a container:

```shell
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

## Azure Extensions Used

- **Quarkus Azure Key Vault**: Manages JWT certificates and secrets
- **Quarkus Azure App Configuration**: Centralized configuration management
- **Quarkus Azure Storage Blob**: Storage for portfolio assets

## Security

- JWT Authentication for admin access
- SSL/TLS for all environments
- Managed identities for secure Azure service access

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- Quarkus Support Azure Key Vault ([guide](https://docs.quarkiverse.io/quarkus-azure-services/dev/quarkus-azure-key-vault.html)): Manage secrets in Azure Key Vault Service
- Security Jakarta Persistence Reactive ([guide](https://quarkus.io/guides/security-getting-started)): Secure your applications with username/password stored in a database via Jakarta Persistence
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it
- Quarkus Azure App Configuration ([guide](https://docs.quarkiverse.io/quarkus-azure-services/dev/quarkus-azure-app-configuration.html)): Quarkus Azure App Configuration
- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin
- OpenID Connect ([guide](https://quarkus.io/guides/security-openid-connect)): Verify Bearer access tokens and authenticate users with Authorization Code Flow
- WebSockets ([guide](https://quarkus.io/guides/websockets)): WebSocket communication channel support
- SmallRye JWT ([guide](https://quarkus.io/guides/security-jwt)): Secure your applications with JSON Web Token
- Quarkus Support Azure Storage Blob ([guide](https://docs.quarkiverse.io/quarkus-azure-services/dev/quarkus-azure-storage-blob.html)): Quarkus Support for Azure Storage Blob