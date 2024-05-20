import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { EmailTemplateUpdateComponent } from 'app/entities/email-template/email-template-update.component';
import { EmailTemplateService } from 'app/entities/email-template/email-template.service';
import { EmailTemplate } from 'app/shared/model/email-template.model';

describe('Component Tests', () => {
  describe('EmailTemplate Management Update Component', () => {
    let comp: EmailTemplateUpdateComponent;
    let fixture: ComponentFixture<EmailTemplateUpdateComponent>;
    let service: EmailTemplateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [EmailTemplateUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmailTemplateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmailTemplateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmailTemplateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmailTemplate(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmailTemplate();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
