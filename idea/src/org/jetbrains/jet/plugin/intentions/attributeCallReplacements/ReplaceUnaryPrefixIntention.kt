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

public class ReplaceUnaryPrefixIntention : AttributeCallReplacementIntention("Unary Prefix") {
    private fun lookup(name: String?) : String? {
        return when (name) {
            "plus" -> "+"
            "minus" -> "-"
            "not" -> "!"
            else -> null
        }
    }
    override fun isApplicableToCall(call: JetCallExpression): Boolean {
        return (
            lookup(call.getFunctionName()) != null &&
            call.getFunctionLiteralArguments().size() == 0 &&
            call.getValueArguments().size() == 0 &&
            call.getValueArgumentList() != null
        )
    }
    override fun makeReplacement(element: JetQualifiedExpression, call: JetCallExpression, receiver: JetExpression) : AttributeCallReplacementIntention.Replacement {
        return AttributeCallReplacementIntention.Replacement(element, lookup(call.getFunctionName())!! + receiver.getText())
    }
}
