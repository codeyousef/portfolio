package code.yousef.styles

import kotlinx.css.*
import kotlinx.css.properties.*
import org.w3c.dom.HTMLStyleElement
import kotlinx.browser.document

/**
 * Global application styles
 */
object AppStyles {
    fun init() {
        // Create a style element
        val styleEl = document.createElement("style") as HTMLStyleElement
        document.head?.appendChild(styleEl)

        // Define styles
        styleEl.innerHTML = """
            :root {
                --primary-rgb: 0, 247, 255;
                --primary: rgb(var(--primary-rgb));
                --secondary-rgb: 255, 42, 109;
                --secondary: rgb(var(--secondary-rgb));
                --dark: #0a0a14;
                --light: #f5f5f7;
                --header-height: 60px;
                --footer-height: 120px;
            }
            
            * {
                box-sizing: border-box;
                margin: 0;
                padding: 0;
            }
            
            body {
                font-family: 'Inter', system-ui, -apple-system, sans-serif;
                background-color: var(--dark);
                color: var(--light);
                line-height: 1.6;
                min-height: 100vh;
            }
            
            #app {
                display: flex;
                flex-direction: column;
                min-height: 100vh;
            }
            
            #content {
                flex: 1;
                padding-top: var(--header-height);
                padding-bottom: 2rem;
                min-height: calc(100vh - var(--header-height) - var(--footer-height));
            }
            
            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 0 1.5rem;
            }
            
            h1, h2, h3, h4, h5, h6 {
                margin-bottom: 1rem;
                line-height: 1.2;
            }
            
            a {
                color: var(--primary);
                text-decoration: none;
                transition: color 0.3s ease;
            }
            
            a:hover {
                color: var(--secondary);
            }
            
            img {
                max-width: 100%;
                height: auto;
            }
            
            button, .button {
                display: inline-block;
                background-color: var(--primary);
                color: var(--dark);
                border: none;
                padding: 0.8rem 1.5rem;
                border-radius: 4px;
                cursor: pointer;
                font-weight: 600;
                transition: all 0.3s ease;
            }
            
            button:hover, .button:hover {
                background-color: var(--secondary);
                transform: translateY(-2px);
            }
            
            .header {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: var(--header-height);
                background-color: rgba(10, 10, 20, 0.9);
                backdrop-filter: blur(8px);
                z-index: 100;
                border-bottom: 1px solid rgba(255, 255, 255, 0.1);
            }
            
            .header-content {
                display: flex;
                justify-content: space-between;
                align-items: center;
                height: 100%;
            }
            
            .logo {
                font-weight: 700;
                font-size: 1.5rem;
                color: var(--primary);
            }
            
            .nav-links {
                display: flex;
                gap: 1.5rem;
            }
            
            .nav-link {
                color: var(--light);
                font-weight: 500;
                position: relative;
            }
            
            .nav-link:hover {
                color: var(--primary);
            }
            
            .nav-link.active {
                color: var(--primary);
            }
            
            .nav-link.active::after {
                content: '';
                position: absolute;
                bottom: -4px;
                left: 0;
                width: 100%;
                height: 2px;
                background-color: var(--primary);
            }
            
            .footer {
                background-color: rgba(10, 10, 20, 0.8);
                padding: 2rem 0;
                border-top: 1px solid rgba(255, 255, 255, 0.1);
                height: var(--footer-height);
            }
            
            .footer-content {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            
            .copyright {
                color: rgba(255, 255, 255, 0.7);
            }
            
            .social-links {
                display: flex;
                gap: 1rem;
            }
            
            .hero {
                min-height: 90vh;
                display: flex;
                align-items: center;
                position: relative;
                overflow: hidden;
            }
            
            .hero-content {
                position: relative;
                z-index: 1;
                max-width: 700px;
            }
            
            .hero h1 {
                font-size: 3rem;
                margin-bottom: 1.5rem;
                background: linear-gradient(45deg, var(--primary), var(--secondary));
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                background-clip: text;
            }
            
            .hero p {
                font-size: 1.2rem;
                margin-bottom: 2rem;
                color: rgba(255, 255, 255, 0.9);
            }
            
            .hero-actions {
                display: flex;
                gap: 1rem;
            }
            
            .section {
                padding: 5rem 0;
            }
            
            .section-header {
                text-align: center;
                margin-bottom: 3rem;
            }
            
            .section-title {
                font-size: 2.5rem;
                margin-bottom: 1rem;
                color: var(--primary);
            }
            
            .projects-grid, .blog-grid, .services-grid {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
                gap: 2rem;
                margin-top: 2rem;
            }
            
            .card {
                background-color: rgba(255, 255, 255, 0.05);
                border-radius: 8px;
                overflow: hidden;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
                border: 1px solid rgba(255, 255, 255, 0.1);
            }
            
            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
            }
            
            .card-image {
                height: 200px;
                width: 100%;
                object-fit: cover;
            }
            
            .card-content {
                padding: 1.5rem;
            }
            
            .card-title {
                font-size: 1.5rem;
                margin-bottom: 0.5rem;
            }
            
            .card-description {
                color: rgba(255, 255, 255, 0.8);
                margin-bottom: 1rem;
            }
            
            .tags {
                display: flex;
                flex-wrap: wrap;
                gap: 0.5rem;
                margin-bottom: 1rem;
            }
            
            .tag {
                background-color: rgba(var(--primary-rgb), 0.2);
                color: var(--primary);
                padding: 0.3rem 0.8rem;
                border-radius: 4px;
                font-size: 0.8rem;
            }
            
            .form-group {
                margin-bottom: 1.5rem;
            }
            
            label {
                display: block;
                margin-bottom: 0.5rem;
                color: rgba(255, 255, 255, 0.9);
            }
            
            input, textarea, select {
                width: 100%;
                padding: 0.8rem;
                background-color: rgba(255, 255, 255, 0.1);
                border: 1px solid rgba(255, 255, 255, 0.2);
                border-radius: 4px;
                color: var(--light);
            }
            
            input:focus, textarea:focus, select:focus {
                outline: none;
                border-color: var(--primary);
            }
            
            textarea {
                min-height: 150px;
                resize: vertical;
            }
            
            @media (max-width: 768px) {
                .hero h1 {
                    font-size: 2.2rem;
                }
                
                .hero p {
                    font-size: 1rem;
                }
                
                .projects-grid, .blog-grid, .services-grid {
                    grid-template-columns: 1fr;
                }
                
                .footer-content {
                    flex-direction: column;
                    gap: 1rem;
                }
            }
        """
    }
}