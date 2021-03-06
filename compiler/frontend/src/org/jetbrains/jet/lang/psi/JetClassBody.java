/*
 * Copyright 2010-2013 JetBrains s.r.o.
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

package org.jetbrains.jet.lang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jet.JetNodeTypes;
import org.jetbrains.jet.lang.psi.stubs.PsiJetClassBodyStub;
import org.jetbrains.jet.lang.psi.stubs.elements.JetStubElementTypes;
import org.jetbrains.jet.lexer.JetTokens;

import java.util.List;

public class JetClassBody extends JetElementImplStub<PsiJetClassBodyStub> implements JetDeclarationContainer {
    public JetClassBody(@NotNull ASTNode node) {
        super(node);
    }

    public JetClassBody(@NotNull PsiJetClassBodyStub stub) {
        super(stub, JetStubElementTypes.CLASS_BODY);
    }

    @Override
    @NotNull
    public List<JetDeclaration> getDeclarations() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, JetDeclaration.class);
    }

    @Override
    public <R, D> R accept(@NotNull JetVisitor<R, D> visitor, D data) {
        return visitor.visitClassBody(this, data);
    }

    @NotNull
    public List<JetClassInitializer> getAnonymousInitializers() {
        return findChildrenByType(JetNodeTypes.ANONYMOUS_INITIALIZER);
    }

    @NotNull
    public List<JetProperty> getProperties() {
        return findChildrenByType(JetNodeTypes.PROPERTY);
    }

    @Nullable
    public JetClassObject getClassObject() {
        return (JetClassObject) findChildByType(JetNodeTypes.CLASS_OBJECT);
    }

    @Nullable
    public PsiElement getRBrace() {
        ASTNode[] children = getNode().getChildren(TokenSet.create(JetTokens.RBRACE));
        return children.length == 1 ? children[0].getPsi() : null;
    }

    @Nullable
    public PsiElement getLBrace() {
        ASTNode[] children = getNode().getChildren(TokenSet.create(JetTokens.LBRACE));
        return children.length == 1 ? children[0].getPsi() : null;
    }
}
