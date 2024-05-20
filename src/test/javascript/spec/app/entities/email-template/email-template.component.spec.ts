import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IngestionApiTestModule } from '../../../test.module';
import { EmailTemplateComponent } from 'app/entities/email-template/email-template.component';
import { EmailTemplateService } from 'app/entities/email-template/email-template.service';
import { EmailTemplate } from 'app/shared/model/email-template.model';

describe('Component Tests', () => {
  describe('EmailTemplate Management Component', () => {
    let comp: EmailTemplateComponent;
    let fixture: ComponentFixture<EmailTemplateComponent>;
    let service: EmailTemplateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [EmailTemplateComponent],
        providers: [],
      })
        .overrideTemplate(EmailTemplateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmailTemplateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmailTemplateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmailTemplate(123)],
            headers,
          }),
        ),
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.emailTemplates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
