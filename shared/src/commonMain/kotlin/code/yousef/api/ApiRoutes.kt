package code.yousef.api

/**
 * Centralized API routes for both backend and frontend
 */
object ApiRoutes {
    // Base API paths
    const val API_BASE = "/api/v1"

    // Projects endpoints
    object Projects {
        const val BASE = "$API_BASE/projects"
        const val FEATURED = "$BASE/featured"
        const val BY_ID = "$BASE/{id}"
    }

    // Blog endpoints
    object Blog {
        const val BASE = "$API_BASE/blog"
        const val BY_SLUG = "$BASE/{slug}"
        const val BY_TAG = "$BASE/tag/{tag}"
    }

    // Services endpoints
    object Services {
        const val BASE = "$API_BASE/services"
        const val BY_ID = "$BASE/{id}"
        const val FEATURED = "$BASE/featured"
    }

    // SEO-friendly page routes (for SSR)
    object Pages {
        const val HOME = "/"
        const val PROJECTS = "/projects"
        const val PROJECT_DETAILS = "/projects/{id}"
        const val BLOG = "/blog"
        const val BLOG_POST = "/blog/{slug}"
        const val SERVICES = "/services"
        const val SERVICE_DETAILS = "/services/{id}"
        const val ABOUT = "/about"
        const val CONTACT = "/contact"
    }
}