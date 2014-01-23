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
import org.jetbrains.jet.lexer.JetTokens
import org.jetbrains.jet.lang.psi.JetPrefixExpression

public class ReplaceContainsIntention : AttributeCallReplacementIntention("contains") {
    override fun isApplicableToCall(call: JetCallExpression): Boolean {
        return (
            call.getFunctionName() == "contains" &&
            call.getFunctionLiteralArguments().size() == 0 &&           // No function literals
            call.getValueArguments().size() == 1 &&                     // One argument
            !call.hasNamedArguments()                                    // No named arguments
        )
    }
    override fun makeReplacement(element: JetQualifiedExpression, call: JetCallExpression, receiver: JetExpression) : AttributeCallReplacementIntention.Replacement {
        val argumentString = call.getValueArguments()[0]!!.getArgumentExpression()
        val receiverString = receiver.getText()
        val parent = element.getParent()

        return if (parent is JetPrefixExpression && parent.getOperationReference().getReferencedNameElementType() == JetTokens.EXCL) {
            AttributeCallReplacementIntention.Replacement(parent, "%s !in %s".format(argumentString, receiverString))
        } else {
            AttributeCallReplacementIntention.Replacement(element, "%s in %s".format(argumentString, receiverString))
        }
    }
}
