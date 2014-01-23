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

package org.jetbrains.jet.plugin.intentions.attributeCallReplacements

import org.jetbrains.jet.lang.psi.JetCallExpression
import com.intellij.openapi.editor.Editor
import org.jetbrains.jet.lang.psi.JetQualifiedExpression
import org.jetbrains.jet.lang.psi.JetExpression
import org.jetbrains.jet.lang.psi.JetDotQualifiedExpression
import org.jetbrains.jet.plugin.JetBundle
import com.intellij.psi.PsiFile
import org.jetbrains.jet.lang.psi.psiUtil.getParentByTypesAndPredicate
import com.intellij.openapi.project.Project
import com.intellij.codeInsight.intention.impl.BaseIntentionAction
import org.jetbrains.jet.lang.psi.JetPsiFactory

public abstract class AttributeCallReplacementIntention(val name: String) : BaseIntentionAction() {
    {
        setText(getFamilyName())
    }

    protected class Replacement(val target: JetExpression, val text: String)

    private val elementType = javaClass<JetDotQualifiedExpression>()

    abstract protected fun isApplicableToCall(call: JetCallExpression) : Boolean
    abstract protected fun makeReplacement(element: JetQualifiedExpression, call: JetCallExpression, receiver: JetExpression) : Replacement

    protected fun getTarget(editor: Editor, file: PsiFile): JetDotQualifiedExpression? {
        val offset = editor.getCaretModel().getOffset()
        return file.findElementAt(offset)?.getParentByTypesAndPredicate(false, elementType) {
            element -> with (element.getSelectorExpression()) {
                when (this) {
                    is JetCallExpression -> isApplicableToCall(this)
                    else -> false
                }
            }
        }
    }

    public override fun isAvailable(project: Project, editor: Editor, file: PsiFile): Boolean {
        return with (getTarget(editor, file)) {
            when(this) {
                null -> false
                else -> {
                    updateText(this, this.getSelectorExpression() as JetCallExpression, this.getReceiverExpression())
                    true
                }
            }
        }
    }

    // Override this to manually specify the intention text.
    protected open fun updateText(element: JetQualifiedExpression, call: JetCallExpression, receiver: JetExpression) {
        val replacement = makeReplacement(element, call, receiver)
        val before = replacement.target.getText()
        val after = replacement.text
        if (before != null)
            setText(before, after)
    }

    protected fun setText(before: String, after: String) {
        setText(JetBundle.message("replace.attribute.call", before, after))
    }


    public override fun getFamilyName() : String {
        return JetBundle.message("replace.attribute.call.family", name)
    }

    public override fun invoke(project: Project, editor: Editor, file: PsiFile): Unit {
        val target = getTarget(editor, file)
        assert(target != null, "Intention is not applicable")
        target!! // It matches

        val selector = target.getSelectorExpression() as JetCallExpression
        val receiver = target.getReceiverExpression()

        val replacement= makeReplacement(target, selector, receiver)
        val newElement = JetPsiFactory.createExpression(
                replacement.target.getProject(),
                replacement.text
        )
        replacement.target.replace(newElement);
    }

    // Helper Functions

    /**
     * Get the name of a call expression if defined.
     */
    protected fun JetCallExpression.getFunctionName() : String? {
        return this.getCalleeExpression()?.getText()
    }
    /**
     * Returns true iff the call expression has named arguments.
     */
    protected fun JetCallExpression.hasNamedArguments() : Boolean {
        return getValueArguments().any { arg -> arg?.getArgumentName() != null }
    }
}
