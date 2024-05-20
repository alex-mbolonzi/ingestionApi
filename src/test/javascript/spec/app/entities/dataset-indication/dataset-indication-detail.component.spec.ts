import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetIndicationDetailComponent } from 'app/entities/dataset-indication/dataset-indication-detail.component';
import { DatasetIndication } from 'app/shared/model/dataset-indication.model';

describe('Component Tests', () => {
  describe('DatasetIndication Management Detail Component', () => {
    let comp: DatasetIndicationDetailComponent;
    let fixture: ComponentFixture<DatasetIndicationDetailComponent>;
    const route = { data: of({ datasetIndication: new DatasetIndication(123) }) } as any as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetIndicationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DatasetIndicationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetIndicationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.datasetIndication).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
