package code.yousef.resource

import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.jboss.resteasy.reactive.RestForm
import org.jboss.resteasy.reactive.multipart.FileUpload
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Path("/upload")
class FileUploadResource {

    class FileUploadForm {
        @RestForm("file")
        lateinit var file: FileUpload
    }

    @POST
    @Path("/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    fun uploadImage(form: FileUploadForm): Response {
        try {
            val uploadDir = "uploads/images"
            val uploadPath = Paths.get(uploadDir)

            // Create directories if they don't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath)
            }

            val fileName = "${UUID.randomUUID()}_${form.file.fileName()}"
            val targetPath = uploadPath.resolve(fileName)

            // Copy the file to the target location
            Files.copy(form.file.uploadedFile(), targetPath)

            // Return the URL to the uploaded file
            return Response.ok(
                mapOf(
                    "url" to "/uploads/images/$fileName",
                    "fileName" to fileName
                )
            ).build()

        } catch (e: IOException) {
            return Response.serverError().entity(
                mapOf(
                    "error" to "Failed to upload file: ${e.message}"
                )
            ).build()
        }
    }

    @POST
    @Path("/model")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    fun uploadModel(form: FileUploadForm): Response {
        try {
            val uploadDir = "uploads/models"
            val uploadPath = Paths.get(uploadDir)

            // Create directories if they don't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath)
            }

            val fileName = "${UUID.randomUUID()}_${form.file.fileName()}"
            val targetPath = uploadPath.resolve(fileName)

            // Copy the file to the target location
            Files.copy(form.file.uploadedFile(), targetPath)

            // Return the URL to the uploaded file
            return Response.ok(
                mapOf(
                    "url" to "/uploads/models/$fileName",
                    "fileName" to fileName
                )
            ).build()

        } catch (e: IOException) {
            return Response.serverError().entity(
                mapOf(
                    "error" to "Failed to upload file: ${e.message}"
                )
            ).build()
        }
    }
}
