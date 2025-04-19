package code.yousef.ui

import code.yousef.summon.runtime.Composable

/**
 * Root component for the application structure.
 * This component simply renders the content without adding any container or styling.
 * This allows the child components to have full control over their styling.
 */
@Composable
fun AppRoot(content: @Composable () -> Unit) {
    // Directly render the content without any wrapper
    content()
}
