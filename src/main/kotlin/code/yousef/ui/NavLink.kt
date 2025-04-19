package code.yousef.ui

import code.yousef.summon.runtime.Composable
import code.yousef.summon.components.display.Text
import code.yousef.summon.components.layout.Box
import code.yousef.summon.modifier.Modifier
import code.yousef.summon.modifier.attribute
import code.yousef.summon.modifier.style

/**
 * A navigation link component for the portfolio site.
 * 
 * @param text The text to display in the link
 * @param href The URL to navigate to
 * @param isActive Whether this link is currently active
 */
@Composable
fun NavLink(text: String, href: String, isActive: Boolean = false) {
    Box(
        modifier = Modifier()
            .style("style", """
                position: relative;
                color: ${if (isActive) "#00f7ff" else "rgba(255, 255, 255, 0.7)"};
                text-decoration: none;
                font-weight: 500;
                padding: 0.25rem 0.5rem;
                transition: color 0.2s ease;
            """)
            .attribute("onmouseover", "this.style.color='#00f7ff';")
            .attribute("onmouseout", "this.style.color='" + (if (isActive) "#00f7ff" else "rgba(255, 255, 255, 0.7)") + "';")
            .attribute("onclick", "window.location.href='" + href + "';")
    ) {
        Text(text = text)
        
        // Active indicator
        if (isActive) {
            Box(
                modifier = Modifier()
                    .style("style", """
                        content: '';
                        position: absolute;
                        bottom: -4px;
                        left: 0;
                        width: 100%;
                        height: 2px;
                        background-color: #00f7ff;
                    """)
            ) {}
        } else {
            Box(
                modifier = Modifier()
                    .style("style", """
                        content: '';
                        position: absolute;
                        bottom: -4px;
                        left: 0;
                        width: 0;
                        height: 2px;
                        background-color: #00f7ff;
                        transition: width 0.3s ease;
                    """)
                    .attribute("onmouseover", "this.style.width='100%';")
                    .attribute("onmouseout", "this.style.width='0';")
            ) {}
        }
    }
}