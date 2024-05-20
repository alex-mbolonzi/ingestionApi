import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { RequestStatusDetailsDetailComponent } from 'app/entities/request-status-details/request-status-details-detail.component';
import { RequestStatusDetails } from 'app/shared/model/request-status-details.model';

describe('Component Tests', () => {
  describe('RequestStatusDetails Management Detail Component', () => {
    let comp: RequestStatusDetailsDetailComponent;
    let fixture: ComponentFixture<RequestStatusDetailsDetailComponent>;
    const route = { data: of({ requestStatusDetails: new RequestStatusDetails(123) }) } as any as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [RequestStatusDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RequestStatusDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RequestStatusDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.requestStatusDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
