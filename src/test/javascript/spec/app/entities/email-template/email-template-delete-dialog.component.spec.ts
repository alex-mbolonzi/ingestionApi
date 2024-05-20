import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IngestionApiTestModule } from '../../../test.module';
import { EmailTemplateDeleteDialogComponent } from 'app/entities/email-template/email-template-delete-dialog.component';
import { EmailTemplateService } from 'app/entities/email-template/email-template.service';

describe('Component Tests', () => {
  describe('EmailTemplate Management Delete Component', () => {
    let comp: EmailTemplateDeleteDialogComponent;
    let fixture: ComponentFixture<EmailTemplateDeleteDialogComponent>;
    let service: EmailTemplateService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [EmailTemplateDeleteDialogComponent],
      })
        .overrideTemplate(EmailTemplateDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmailTemplateDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmailTemplateService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        }),
      ));
    });
  });
});
