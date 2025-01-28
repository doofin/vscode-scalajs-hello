package vscextension.facade

import scala.scalajs.js
import scala.scalajs.js.annotation._
import scala.scalajs.js.Promise

object lspFacade {
  @js.native
  @JSImport("vscode-languageclient/node", JSImport.Default)
  /** not working due to weird/bad module organization
    *
    * Cannot find module 'vscode-languageclient/node'
    *
    * @param id
    * @param name
    * @param serverOptions
    * @param clientOptions
    */
  class LanguageClient(
      id: String,
      name: String,
      serverOptions: js.Object,
      clientOptions: js.Object
  ) extends js.Object {

    /* the code correspond to below, but it's not working
    https://github.com/microsoft/vscode-extension-samples/blob/main/lsp-sample/client/src/extension.ts
    import {
      LanguageClient,
      LanguageClientOptions,
      ServerOptions,
      TransportKind
    } from 'vscode-languageclient/node';

    let client: LanguageClient;

     */
  }

  @js.native
  trait ServerOptions extends js.Function0[Promise[MessageTransports]] {
    def apply(): Promise[MessageTransports] = js.native
  }

  @js.native
  trait MessageTransports extends js.Object

  @js.native
  trait LanguageClientOptions extends js.Object

}
