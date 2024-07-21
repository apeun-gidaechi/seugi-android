package com.seugi.designsystem.component

import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.controllers.RiveFileController
import app.rive.runtime.kotlin.core.Alignment
import app.rive.runtime.kotlin.core.ExperimentalAssetLoader
import app.rive.runtime.kotlin.core.Fit
import app.rive.runtime.kotlin.core.Loop
import app.rive.runtime.kotlin.core.PlayableInstance
import app.rive.runtime.kotlin.core.Rive
import com.seugi.designsystem.R
import com.seugi.designsystem.theme.Black

@ExperimentalAssetLoader
@Suppress("LongMethod")
@Composable
fun RiveAnimation(
    modifier: Modifier = Modifier,
    @RawRes resId: Int,
    autoplay: Boolean = true,
    artboardName: String? = null,
    animationName: String? = null,
    stateMachineName: String? = null,
    fit: Fit = Fit.CONTAIN,
    alignment: Alignment = Alignment.CENTER,
    loop: Loop = Loop.AUTO,
    contentDescription: String?,
    notifyLoop: ((PlayableInstance) -> Unit)? = null,
    notifyPause: ((PlayableInstance) -> Unit)? = null,
    notifyPlay: ((PlayableInstance) -> Unit)? = null,
    notifyStateChanged: ((String, String) -> Unit)? = null,
    notifyStop: ((PlayableInstance) -> Unit)? = null,
    update: (RiveAnimationView) -> Unit = { _ -> },
) {
    var riveAnimationView: RiveAnimationView? = null
    var listener: RiveFileController.Listener? = null
    val lifecycleOwner = LocalLifecycleOwner.current

    if (LocalInspectionMode.current) { // For Developing only,
        Image(
            modifier = modifier.size(100.dp),
            painter = painterResource(id = androidx.core.R.drawable.ic_call_answer),
            contentDescription = contentDescription,
        )
    } else {
        val semantics = if (contentDescription != null) {
            Modifier.semantics {
                this.contentDescription = contentDescription
                this.role = Role.Image
            }
        } else {
            Modifier
        }
        listener = object : RiveFileController.Listener {
            override fun notifyLoop(animation: PlayableInstance) {
                notifyLoop?.invoke(animation)
            }

            override fun notifyPause(animation: PlayableInstance) {
                notifyPause?.invoke(animation)
            }

            override fun notifyPlay(animation: PlayableInstance) {
                notifyPlay?.invoke(animation)
            }

            override fun notifyStateChanged(stateMachineName: String, stateName: String) {
                notifyStateChanged?.invoke(stateMachineName, stateName)
            }

            override fun notifyStop(animation: PlayableInstance) {
                notifyStop?.invoke(animation)
            }
        }.takeIf {
            (notifyLoop != null) || (notifyPause != null) ||
                (notifyPlay != null) || (notifyStateChanged != null) ||
                (notifyStop != null)
        }

        AndroidView(
            modifier = modifier
                .then(semantics)
                .clipToBounds(),
            factory = { context ->
                riveAnimationView = RiveAnimationView(context).apply {
                    setRiveResource(
                        resId,
                        artboardName,
                        animationName,
                        stateMachineName,
                        autoplay,
                        fit,
                        alignment,
                        loop,
                    )
                }
                listener?.let {
                    riveAnimationView?.registerListener(it)
                }
                riveAnimationView!!
            },
            update = {
                update.invoke(it)
            },
        )

        DisposableEffect(lifecycleOwner) {
            onDispose {
                listener?.let {
                    riveAnimationView?.unregisterListener(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalAssetLoader::class)
@Composable
@Preview(showBackground = true)
fun RiveComposablePreview() {
    val context = LocalContext.current
    Rive.init(context)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
    ) {
        RiveAnimation(
            modifier = Modifier.size(24.dp),
            resId = R.raw.loading_dots,
            autoplay = true,
            animationName = "Loading",
            contentDescription = "Just a Rive Animation",
        )
    }
}
