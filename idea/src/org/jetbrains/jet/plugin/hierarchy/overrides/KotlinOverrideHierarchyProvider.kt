/*
 * Copyright 2010-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.plugin.hierarchy.overrides

import com.intellij.ide.hierarchy.HierarchyProvider
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.psi.PsiElement
import com.intellij.ide.hierarchy.HierarchyBrowser
import com.intellij.openapi.actionSystem.CommonDataKeys
import org.jetbrains.jet.plugin.hierarchy.HierarchyUtils
import com.intellij.ide.hierarchy.MethodHierarchyBrowserBase
import com.intellij.ide.hierarchy.HierarchyBrowserBaseEx

public class KotlinOverrideHierarchyProvider: HierarchyProvider {
    override fun getTarget(dataContext: DataContext): PsiElement? {
        return CommonDataKeys.PROJECT.getData(dataContext)?.let { project ->
            HierarchyUtils.getOverrideHierarchyElement(HierarchyUtils.getCurrentElement(dataContext, project))
        }
    }

    override fun createHierarchyBrowser(target: PsiElement): HierarchyBrowser =
            KotlinOverrideHierarchyBrowser(target.getProject(), target)

    override fun browserActivated(hierarchyBrowser: HierarchyBrowser) {
        (hierarchyBrowser as HierarchyBrowserBaseEx).changeView(MethodHierarchyBrowserBase.METHOD_TYPE)
    }
}
