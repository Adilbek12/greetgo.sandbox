package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Json;
import kz.greetgo.mvc.annotations.Mapping;
import kz.greetgo.mvc.annotations.MethodFilter;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.core.RequestMethod;
import kz.greetgo.mvc.interfaces.BinResponse;
import kz.greetgo.sandbox.controller.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.FileTypeEnum;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.render.impl.ClientRenderImplPdf;
import kz.greetgo.sandbox.controller.render.impl.ClientRenderImplXlsx;
import kz.greetgo.sandbox.controller.util.Controller;

import java.io.IOException;

@Bean
@Mapping("/record")
public class RecordController implements Controller {

  public BeanGetter<ClientRegister> clientRegister;

  @MethodFilter(RequestMethod.GET)
  @Mapping("/get-render")
  public void render(@Par("fileName") String fileName, @Par("clientFilter") @Json ClientFilter filter,
                     @Par("fileTypeEnum") @Json FileTypeEnum fileType, BinResponse binResponse) {
    switch (fileType) {
      case XLSX:
        binResponse.setFilename(String.format("%s.xlsx",fileName));
        binResponse.setContentTypeByFilenameExtension();
        clientRegister.get().renderClientList(fileName, filter, new ClientRenderImplXlsx(binResponse.out()));
        break;
      case PDF:
        binResponse.setFilename(String.format("%s.pdf",fileName));
        binResponse.setContentTypeByFilenameExtension();
        clientRegister.get().renderClientList(fileName, filter, new ClientRenderImplPdf(binResponse.out()));
        try {
          binResponse.out().flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
    binResponse.flushBuffers();
  }
}