/*
 * Copyright 2014 JetBrains s.r.o.
 * * Licensed under the Apache License, Version 2.0 (the "License");
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
import com.intellij.psi.tree.TokenSet
import org.jetbrains.jet.lexer.JetTokens
import org.jetbrains.jet.JetNodeTypes
import com.intellij.openapi.util.text.StringUtil

public class ReplaceGetIntention : AttributeCallReplacementIntention("get") {
    override fun isApplicableToCall(call: JetCallExpression): Boolean {
        return (
            call.getFunctionName() == "get" &&
            call.getValueArguments().size() > 0 &&              // Must have at least one argument
            call.getFunctionLiteralArguments().size() == 0 &&   // Must have no function literal arguments
            !call.hasNamedArguments()                           // Must have no named arguments
        )
    }
    override fun makeReplacement(element: JetQualifiedExpression, call: JetCallExpression, receiver: JetExpression) : AttributeCallReplacementIntention.Replacement {
        val argumentsString = call.getArgumentsString()
        val receiverString = receiver.getText()

        return AttributeCallReplacementIntention.Replacement(element, "%s[%s]".format(receiverString, argumentsString))
    }


    // Do this to preserve whitespace, comments, etc.
    private val ARGUMENTS_TOKEN = TokenSet.orSet(JetTokens.WHITE_SPACE_OR_COMMENT_BIT_SET, TokenSet.create(JetTokens.COMMA, JetNodeTypes.VALUE_ARGUMENT))
    protected fun JetCallExpression.getArgumentsString() : String? {
        val args = this.getValueArgumentList()?.getNode()?.getChildren(ARGUMENTS_TOKEN)
        if (args == null)
            return null
        return StringUtil.join(args.map { it.getText() }, "")
    }

}
