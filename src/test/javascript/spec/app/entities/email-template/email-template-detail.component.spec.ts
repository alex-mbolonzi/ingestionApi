import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { EmailTemplateDetailComponent } from 'app/entities/email-template/email-template-detail.component';
import { EmailTemplate } from 'app/shared/model/email-template.model';

describe('Component Tests', () => {
  describe('EmailTemplate Management Detail Component', () => {
    let comp: EmailTemplateDetailComponent;
    let fixture: ComponentFixture<EmailTemplateDetailComponent>;
    const route = { data: of({ emailTemplate: new EmailTemplate(123) }) } as any as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [EmailTemplateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmailTemplateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmailTemplateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emailTemplate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
