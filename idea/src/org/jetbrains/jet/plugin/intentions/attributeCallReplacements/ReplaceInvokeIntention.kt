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
import org.jetbrains.jet.lang.psi.JetQualifiedExpression
import org.jetbrains.jet.lang.psi.JetExpression

public class ReplaceInvokeIntention : AttributeCallReplacementIntention("invoke") {
    override fun isApplicableToCall(call: JetCallExpression): Boolean {
        return call.getFunctionName() == "invoke"
    }
    fun render(call: JetCallExpression, receiver: JetExpression) : String {
        return receiver.getText() + (call.getValueArgumentList()?.getText() ?: "")
    }

    override fun makeReplacement(element: JetQualifiedExpression, call: JetCallExpression, receiver: JetExpression) : AttributeCallReplacementIntention.Replacement {
        val fnArgumentsString = call.getFunctionLiteralArguments().fold("") { a, b -> a + " " + b.getText() }
        return AttributeCallReplacementIntention.Replacement(element, render(call, receiver) + fnArgumentsString)
    }

    override fun updateText(element: JetQualifiedExpression, call: JetCallExpression, receiver: JetExpression) {
        val original = receiver.getText() + ".invoke" + (call.getValueArgumentList()?.getText() ?: "")
        val text = render(call, receiver)
        setText(original, text)
    }
}
