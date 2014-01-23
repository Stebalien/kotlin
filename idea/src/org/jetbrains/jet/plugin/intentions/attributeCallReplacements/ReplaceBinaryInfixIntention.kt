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

public class ReplaceBinaryInfixIntention : AttributeCallReplacementIntention("Binary Infix") {
    private fun lookup(name: String?) : String? {
        return when (name) {
            "plus" -> " + "
            "minus" -> " - "
            "div" -> " / "
            "times" -> " * "
            "mod" -> " % "
            "rangeTo" -> ".."
            else -> null
        }
    }
    override fun isApplicableToCall(call: JetCallExpression): Boolean {
        return (
            // valid operator
            lookup(call.getFunctionName()) != null &&
            // Exactly one argument
            call.getFunctionLiteralArguments().size() + call.getValueArguments().size() == 1 &&
            // If a value argument exists, it had better not be named
            !call.hasNamedArguments()
        )
    }
    override fun makeReplacement(element: JetQualifiedExpression, call: JetCallExpression, receiver: JetExpression) : AttributeCallReplacementIntention.Replacement {
        val valueArguments = call.getValueArguments()
        val functionLiteralArguments = call.getFunctionLiteralArguments()

        val argumentString = (
            when {
                valueArguments.size() == 1           -> valueArguments[0]?.getArgumentExpression()
                functionLiteralArguments.size() == 1 -> functionLiteralArguments[0]
                else                                 -> throw RuntimeException("This is impossible!")
            }
        )!!.getText()

        val opString = lookup(call.getFunctionName())!!
        val receiverString = receiver.getText()
        return AttributeCallReplacementIntention.Replacement(element, receiverString + opString + argumentString)
    }
}
